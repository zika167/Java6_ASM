# Quick Startup Guide - Production Features

## 🚀 Testing the New Production Features

### 1. Start the Application

#### Option A: Development Mode (Default)
```bash
cd ASM_BE

# Start MariaDB (if not running)
docker-compose up -d

# Start application
./mvnw spring-boot:run
```

#### Option B: Production Mode
```bash
cd ASM_BE

# Start with production profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### 2. Access Swagger Documentation

Once the application starts, access the API documentation:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs JSON**: http://localhost:8080/v3/api-docs

#### Testing Authentication in Swagger
1. Click "Authorize" button in Swagger UI
2. For Bearer Authentication: Enter `Bearer your-jwt-token`
3. For Session Authentication: Login through web interface first

### 3. Check Logging Features

#### Console Logs (Development)
- Colorized output with different log levels
- Real-time application events
- SQL queries (in development mode)

#### File Logs
```bash
# View application logs
tail -f logs/app.log

# View error logs only
tail -f logs/error.log

# Check log rotation
ls -la logs/
```

### 4. Test API Endpoints

#### Public Endpoints (No Authentication)
```bash
# Get all products
curl http://localhost:8080/api/v1/products

# Get checkout configuration
curl http://localhost:8080/api/v1/config/checkout

# Health check
curl http://localhost:8080/actuator/health
```

#### Protected Endpoints (Requires Login)
```bash
# Login first through web interface: http://localhost:8080/login
# Then access cart API
curl -b cookies.txt http://localhost:8080/api/v1/cart/details
```

### 5. Environment Switching

#### Check Current Profile
```bash
# Look for this in logs:
# "The following profiles are active: [profile-name]"
```

#### Switch to Production Profile
```bash
# Method 1: Command line
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Method 2: Environment variable
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run

# Method 3: Application argument
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 6. Verify Production Settings

When running in production mode, verify:

#### Database Settings
- No SQL queries in logs
- Connection pooling active
- Schema validation only (no auto-creation)

#### Performance Features
- Template caching enabled
- Response compression active
- Static resource caching headers

#### Security Features
- Secure session cookies
- Error details hidden
- Actuator endpoints secured

### 7. Monitoring and Maintenance

#### Log Files Location
```
ASM_BE/logs/
├── app.log                    # Main application log
├── app.2024-04-14.0.log      # Rotated logs
├── error.log                  # Error-only logs
└── error.2024-04-14.log      # Rotated error logs
```

#### Health Endpoints
- **Application Health**: http://localhost:8080/actuator/health
- **Application Info**: http://localhost:8080/actuator/info
- **Metrics**: http://localhost:8080/actuator/metrics

### 8. Troubleshooting

#### Common Issues

**Swagger UI not loading**
- Check if application started successfully
- Verify port 8080 is not blocked
- Check logs for any startup errors

**Database connection issues**
- Ensure MariaDB container is running: `docker ps`
- Check database credentials in application.properties
- Verify port 3306 is accessible

**Log files not created**
- Check if logs directory exists: `mkdir -p logs`
- Verify write permissions on logs directory
- Check logback-spring.xml configuration

**Profile not switching**
- Verify profile name spelling: `prod` (not `production`)
- Check application startup logs for active profiles
- Ensure application-prod.properties exists

### 9. Development Workflow

#### Daily Development
```bash
# Start database
docker-compose up -d

# Start application (development mode)
./mvnw spring-boot:run

# Access Swagger for API testing
open http://localhost:8080/swagger-ui.html

# Monitor logs
tail -f logs/app.log
```

#### Production Testing
```bash
# Build application
./mvnw clean package

# Test production profile locally
java -jar target/ASM_BE-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod

# Verify production settings in logs
```

### 10. Next Steps

After verifying the production features:

1. **SSL Configuration**: Add HTTPS certificates for production
2. **External Database**: Configure production database connection
3. **Monitoring Setup**: Integrate with monitoring tools (Prometheus, Grafana)
4. **CI/CD Pipeline**: Set up automated deployment
5. **Load Testing**: Test application under load
6. **Backup Strategy**: Implement database backup procedures

---

**Happy Testing!** 🎉

The application now includes enterprise-grade features ready for production deployment.