// Main JavaScript file for VDI POC Web Application

document.addEventListener('DOMContentLoaded', function () {
    console.log('VDI POC Web Application loaded');

    // Add smooth scrolling to all links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });

    // Add fade-in animation to cards on scroll
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, observerOptions);

    // Observe all cards
    document.querySelectorAll('.card, .feature-card').forEach(card => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        observer.observe(card);
    });

    // Add active class to current page in navigation
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-menu a').forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });

    // Dashboard auto-refresh functionality
    if (currentPath === '/dashboard') {
        initDashboard();
    }
});

// Dashboard initialization
function initDashboard() {
    console.log('Dashboard initialized');

    // Simulate real-time updates
    setInterval(function () {
        updateDashboardStats();
    }, 5000);
}

// Update dashboard statistics
function updateDashboardStats() {
    // In a real application, this would fetch data from the API
    console.log('Updating dashboard stats...');

    // Example: Fetch health status
    fetch('/api/health')
        .then(response => response.json())
        .then(data => {
            console.log('Health check:', data);
        })
        .catch(error => {
            console.error('Error fetching health status:', error);
        });
}

// Utility function to format dates
function formatDate(date) {
    const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    };
    return new Date(date).toLocaleDateString(undefined, options);
}

// Utility function to animate numbers
function animateValue(element, start, end, duration) {
    let startTimestamp = null;
    const step = (timestamp) => {
        if (!startTimestamp) startTimestamp = timestamp;
        const progress = Math.min((timestamp - startTimestamp) / duration, 1);
        element.textContent = Math.floor(progress * (end - start) + start);
        if (progress < 1) {
            window.requestAnimationFrame(step);
        }
    };
    window.requestAnimationFrame(step);
}

// Handle errors globally
window.addEventListener('error', function (e) {
    console.error('Application error:', e.error);
});

// Export functions for use in other scripts
window.VDI = {
    formatDate: formatDate,
    animateValue: animateValue
};

