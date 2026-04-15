// Cart count functionality
function loadCartCount() {
    axios.get('/api/v1/cart/count')
        .then(response => {
            console.log('Cart count response:', response.data);
            // Check status code instead of success field
            if (response.data.status === 200 || response.status === 200) {
                const count = response.data.data || 0;
                updateCartCount(count);
            }
        })
        .catch(error => {
            console.error('Error loading cart count:', error);
            // If 401, user is not authenticated, hide cart count
            if (error.response && error.response.status === 401) {
                updateCartCount(0);
            }
        });
}

function updateCartCount(count) {
    const cartCountElements = document.querySelectorAll('.cart-count');
    cartCountElements.forEach(element => {
        element.textContent = count;
        if (count > 0) {
            element.style.display = 'flex';
        } else {
            element.style.display = 'none';
        }
    });
}

// Load cart count when page loads
document.addEventListener('DOMContentLoaded', function() {
    loadCartCount();
});
