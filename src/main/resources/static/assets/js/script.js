/* ========================================
   SAMYAK - MAIN JAVASCRIPT FILE
   ======================================== */

// ==========================================
// DOM ELEMENTS
// ==========================================

const menuToggle = document.querySelector('.menu-toggle');
const navMenu = document.querySelector('.nav-menu');
const navLinks = document.querySelectorAll('.nav-link');
const navbar = document.querySelector('.navbar');

// ==========================================
// NAVBAR FUNCTIONALITY
// ==========================================

// Toggle Mobile Menu
if (menuToggle) {
    menuToggle.addEventListener('click', () => {
        menuToggle.classList.toggle('active');
        navMenu.classList.toggle('active');
    });
}

// Close menu when clicking on a link
navLinks.forEach(link => {
    link.addEventListener('click', () => {
        menuToggle.classList.remove('active');
        navMenu.classList.remove('active');
    });
});

// Sticky navbar on scroll
window.addEventListener('scroll', () => {
    if (window.scrollY > 100) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

// Close menu when clicking outside
document.addEventListener('click', (event) => {
    const isClickInsideNav = navbar.contains(event.target);
    if (!isClickInsideNav && navMenu.classList.contains('active')) {
        menuToggle.classList.remove('active');
        navMenu.classList.remove('active');
    }
});

// ==========================================
// SMOOTH SCROLL FOR ANCHOR LINKS
// ==========================================

document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const targetId = this.getAttribute('href');
        if (targetId && targetId !== '#') {
            const targetElement = document.querySelector(targetId);
            if (targetElement) {
                targetElement.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        }
    });
});

// ==========================================
// FORM VALIDATION & SUBMISSION
// ==========================================

const contactForm = document.querySelector('.contact-form form');

if (contactForm) {
    contactForm.addEventListener('submit', function (e) {
        e.preventDefault();

        // Get form values
        const name = this.querySelector('input[name="name"]').value.trim();
        const email = this.querySelector('input[name="email"]').value.trim();
        const message = this.querySelector('textarea[name="message"]').value.trim();

        // Simple validation
        if (!name || !email || !message) {
            showNotification('Please fill in all fields', 'error');
            return;
        }

        // Email validation
        if (!isValidEmail(email)) {
            showNotification('Please enter a valid email address', 'error');
            return;
        }

        // Show loading state
        const submitButton = this.querySelector('button[type="submit"]');
        const originalText = submitButton.textContent;
        submitButton.textContent = 'Sending...';
        submitButton.disabled = true;

        // Submit form via AJAX
        const formData = new FormData(this);

        fetch('contact-form-handler.php', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showNotification('Message sent successfully!', 'success');
                this.reset();
            } else {
                showNotification(data.message || 'An error occurred. Please try again.', 'error');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            showNotification('An error occurred. Please try again later.', 'error');
        })
        .finally(() => {
            submitButton.textContent = originalText;
            submitButton.disabled = false;
        });
    });
}

// ==========================================
// UTILITY FUNCTIONS
// ==========================================

// Email validation
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Show notification
function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.textContent = message;

    // Add styles if not already in CSS
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 25px;
        border-radius: 8px;
        font-weight: 600;
        z-index: 2000;
        animation: slideInRight 0.3s ease;
        max-width: 400px;
        word-wrap: break-word;
    `;

    // Set background color based on type
    if (type === 'success') {
        notification.style.background = '#27ae60';
        notification.style.color = '#fff';
    } else if (type === 'error') {
        notification.style.background = '#e74c3c';
        notification.style.color = '#fff';
    } else {
        notification.style.background = '#3498db';
        notification.style.color = '#fff';
    }

    document.body.appendChild(notification);

    // Remove notification after 4 seconds
    setTimeout(() => {
        notification.style.animation = 'slideOutRight 0.3s ease';
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 4000);
}

// ==========================================
// SCROLL ANIMATIONS
// ==========================================

// Observe elements for fade-in animation on scroll
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add('fade-in');
            observer.unobserve(entry.target);
        }
    });
}, observerOptions);

// Observe all cards and sections
document.querySelectorAll('.card, .section, .program-card, .publication-item').forEach(el => {
    observer.observe(el);
});

// ==========================================
// GALLERY LIGHTBOX (OPTIONAL)
// ==========================================

const galleryItems = document.querySelectorAll('.gallery-item');

galleryItems.forEach(item => {
    item.addEventListener('click', function () {
        const overlayText = this.querySelector('.gallery-overlay-text');
        if (overlayText) {
            console.log('Gallery item clicked:', overlayText.textContent);
            // You can add lightbox functionality here
        }
    });
});

// ==========================================
// PARALLAX EFFECT (OPTIONAL)
// ==========================================

function applyParallax() {
    const parallaxElements = document.querySelectorAll('[data-parallax]');

    parallaxElements.forEach(element => {
        const speed = element.getAttribute('data-parallax') || 0.5;
        const yOffset = window.scrollY * speed;
        element.style.transform = `translateY(${yOffset}px)`;
    });
}

window.addEventListener('scroll', () => {
    applyParallax();
});

// ==========================================
// LAZY LOADING IMAGES
// ==========================================

if ('IntersectionObserver' in window) {
    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                if (img.dataset.src) {
                    img.src = img.dataset.src;
                    img.removeAttribute('data-src');
                }
                imageObserver.unobserve(img);
            }
        });
    });

    document.querySelectorAll('img[data-src]').forEach(img => imageObserver.observe(img));
}

// ==========================================
// SMOOTH SCROLL BEHAVIOR
// ==========================================

document.querySelectorAll('a[href*="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        const href = this.getAttribute('href');
        if (href.startsWith('#') && href.length > 1) {
            e.preventDefault();
            const target = document.querySelector(href);
            if (target) {
                target.scrollIntoView({ behavior: 'smooth' });
            }
        }
    });
});

// ==========================================
// ACTIVE NAVIGATION LINK ON SCROLL
// ==========================================

function updateActiveLink() {
    const sections = document.querySelectorAll('[id]');

    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;

        if (window.scrollY >= sectionTop - 100) {
            navLinks.forEach(link => link.classList.remove('active'));

            const activeLink = document.querySelector(`.nav-link[href="#${section.id}"]`);
            if (activeLink) {
                activeLink.classList.add('active');
            }
        }
    });
}

window.addEventListener('scroll', updateActiveLink);

// ==========================================
// KEYBOARD SHORTCUTS
// ==========================================

document.addEventListener('keydown', (e) => {
    // Close mobile menu with ESC key
    if (e.key === 'Escape' && navMenu.classList.contains('active')) {
        menuToggle.classList.remove('active');
        navMenu.classList.remove('active');
    }
});

// ==========================================
// INITIALIZATION
// ==========================================

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
    console.log('Samyak website loaded successfully');

    // Add fade-in animation to first hero section
    const hero = document.querySelector('.hero');
    if (hero) {
        hero.style.animation = 'fadeIn 0.6s ease';
    }
});

// ==========================================
// CSS FOR ANIMATIONS (if not in main CSS)
// ==========================================

const style = document.createElement('style');
style.textContent = `
    @keyframes slideInRight {
        from {
            opacity: 0;
            transform: translateX(30px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }

    @keyframes slideOutRight {
        from {
            opacity: 1;
            transform: translateX(0);
        }
        to {
            opacity: 0;
            transform: translateX(30px);
        }
    }

    /* Notification styles */
    .notification {
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        border-radius: 8px;
    }

    .notification-success {
        background: #27ae60;
    }

    .notification-error {
        background: #e74c3c;
    }

    .notification-info {
        background: #3498db;
    }
`;

document.head.appendChild(style);

// ==========================================
// UTILITY: THROTTLE FUNCTION
// ==========================================

function throttle(func, limit) {
    let inThrottle;
    return function(...args) {
        if (!inThrottle) {
            func.apply(this, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// Throttle scroll event for better performance
window.addEventListener('scroll', throttle(() => {
    updateActiveLink();
    applyParallax();
}, 100));

// ==========================================
// CONSOLE EASTER EGG
// ==========================================

console.log('%cWelcome to Samyak', 'color: #8B5A2B; font-size: 20px; font-weight: bold;');
console.log('%cCentre for Sanskrit Heritage & Knowledge', 'color: #d4a762; font-size: 14px;');
console.log('%cPreserving Ancient Wisdom | Inspiring Future Generations', 'color: #666; font-size: 12px;');
