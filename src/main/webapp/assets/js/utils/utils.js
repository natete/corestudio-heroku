/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 31/12/15.
 */
(function () {

    /**
     * Compares the date with the given date ignoring datetime
     * @param date
     * @returns {boolean} if both dates are the same ignoring datetime
     */
    Date.prototype.isSameDay = function(date) {
        return this.getDate() === date.getDate()
            && this.getMonth() === date.getMonth()
            && this.getFullYear() === date.getFullYear();
    }

    Date.prototype.toJSON = function() {
        this.setHours(0);
        return this.toISOString();
    }

})();

