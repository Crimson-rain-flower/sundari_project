document.addEventListener('DOMContentLoaded', () => {
    // Alert user on successful click (visual feedback)
    const submitBtn = document.querySelector('.btn-submit');
    if(submitBtn) {
        submitBtn.addEventListener('click', () => {
            console.log("Processing your booking, Ace...");
        });
    }

    // Smooth scroll for nav links
    document.querySelectorAll('nav a').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            target.scrollIntoView({ behavior: 'smooth' });
        });
    });
});