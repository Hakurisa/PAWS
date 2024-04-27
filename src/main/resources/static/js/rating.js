document.addEventListener("DOMContentLoaded", function() {
    const stars = document.querySelectorAll(".star");
    const ratingInput = document.getElementById("pocethvezd");

    stars.forEach(function(star) {
        star.addEventListener("click", function() {
            const value = parseInt(this.getAttribute("data-value"));
            ratingInput.value = value;
            highlightStars(value);
        });
    });

    function highlightStars(value) {
        stars.forEach(function(star) {
            if (parseInt(star.getAttribute("data-value")) <= value) {
                star.classList.add("selected");
            } else {
                star.classList.remove("selected");
            }
        });
    }
});