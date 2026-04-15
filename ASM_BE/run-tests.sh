#!/bin/bash

# Java 6 E-Commerce Test Runner Script

echo "🧪 Java 6 E-Commerce Test Suite"
echo "================================"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to run tests with colored output
run_test() {
    local test_name=$1
    local test_pattern=$2
    
    echo -e "\n${YELLOW}Running $test_name...${NC}"
    
    if ./mvnw test -Dtest="$test_pattern" -q; then
        echo -e "${GREEN}✅ $test_name PASSED${NC}"
        return 0
    else
        echo -e "${RED}❌ $test_name FAILED${NC}"
        return 1
    fi
}

# Main test execution
main() {
    local failed_tests=0
    
    echo "Starting test execution..."
    
    # Run CartService tests
    if ! run_test "CartService Unit Tests" "CartServiceImplTest"; then
        ((failed_tests++))
    fi
    
    # Run ProductService tests
    if ! run_test "ProductService Unit Tests" "ProductServiceImplTest"; then
        ((failed_tests++))
    fi
    
    # Run all service tests together
    if ! run_test "All Service Unit Tests" "*ServiceImplTest"; then
        ((failed_tests++))
    fi
    
    # Summary
    echo -e "\n📊 Test Summary"
    echo "==============="
    
    if [ $failed_tests -eq 0 ]; then
        echo -e "${GREEN}🎉 All tests passed successfully!${NC}"
        echo -e "${GREEN}✅ CartService: Comprehensive cart operations tested${NC}"
        echo -e "${GREEN}✅ ProductService: Product management operations tested${NC}"
        echo -e "${GREEN}✅ Exception Handling: Error scenarios covered${NC}"
        exit 0
    else
        echo -e "${RED}❌ $failed_tests test suite(s) failed${NC}"
        echo -e "${YELLOW}💡 Check the test output above for details${NC}"
        exit 1
    fi
}

# Check if Maven wrapper exists
if [ ! -f "./mvnw" ]; then
    echo -e "${RED}❌ Maven wrapper (mvnw) not found!${NC}"
    echo "Please run this script from the ASM_BE directory"
    exit 1
fi

# Run main function
main "$@"