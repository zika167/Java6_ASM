// Main JavaScript for Precision Digital E-Commerce

// Global utilities
window.PrecisionDigital = {
    // Show notification
    showNotification: function(message, type = 'success') {
        const notification = document.createElement('div');
        notification.className = `notification ${type}`;
        notification.textContent = message;
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease forwards';
            setTimeout(() => {
                if (notification.parentNode) {
                    notification.parentNode.removeChild(notification);
                }
            }, 300);
        }, 3000);
    },

    // Format currency
    formatCurrency: function(amount) {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    },

    // Format date
    formatDate: function(date) {
        return new Intl.DateTimeFormat('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        }).format(new Date(date));
    },

    // Debounce function
    debounce: function(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    },

    // API helper
    api: {
        get: async function(url, options = {}) {
            try {
                const response = await axios.get(url, {
                    withCredentials: true,
                    ...options
                });
                return response.data;
            } catch (error) {
                this.handleError(error);
                throw error;
            }
        },

        post: async function(url, data, options = {}) {
            try {
                const response = await axios.post(url, data, {
                    withCredentials: true,
                    ...options
                });
                return response.data;
            } catch (error) {
                this.handleError(error);
                throw error;
            }
        },

        put: async function(url, data, options = {}) {
            try {
                const response = await axios.put(url, data, {
                    withCredentials: true,
                    ...options
                });
                return response.data;
            } catch (error) {
                this.handleError(error);
                throw error;
            }
        },

        delete: async function(url, options = {}) {
            try {
                const response = await axios.delete(url, {
                    withCredentials: true,
                    ...options
                });
                return response.data;
            } catch (error) {
                this.handleError(error);
                throw error;
            }
        },

        handleError: function(error) {
            if (error.response?.status === 401) {
                window.location.href = '/login';
            } else if (error.response?.status === 403) {
                PrecisionDigital.showNotification('Access denied', 'error');
            } else if (error.response?.data?.message) {
                PrecisionDigital.showNotification(error.response.data.message, 'error');
            } else {
                PrecisionDigital.showNotification('An error occurred', 'error');
            }
        }
    },

    // Cart utilities
    cart: {
        updateBadge: async function() {
            try {
                const response = await PrecisionDigital.api.get('/api/v1/cart/count');
                const badge = document.querySelector('.cart-badge');
                if (badge && response.status === 200) {
                    const count = response.data;
                    badge.textContent = count;
                    badge.style.display = count > 0 ? 'block' : 'none';
                }
            } catch (error) {
                // Silently fail for cart badge update
            }
        }
    },

    // Form validation
    validation: {
        email: function(email) {
            const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            return re.test(email);
        },

        phone: function(phone) {
            const re = /^[\+]?[1-9][\d]{0,15}$/;
            return re.test(phone.replace(/\s/g, ''));
        },

        required: function(value) {
            return value && value.trim().length > 0;
        }
    }
};

// Add slideOut animation
const style = document.createElement('style');
style.textContent = `
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);

// Initialize cart badge on page load
document.addEventListener('DOMContentLoaded', function() {
    PrecisionDigital.cart.updateBadge();
});

// Global error handler for unhandled promise rejections
window.addEventListener('unhandledrejection', function(event) {
    console.error('Unhandled promise rejection:', event.reason);
    PrecisionDigital.showNotification('An unexpected error occurred', 'error');
});

// Export for module systems
if (typeof module !== 'undefined' && module.exports) {
    module.exports = PrecisionDigital;
}