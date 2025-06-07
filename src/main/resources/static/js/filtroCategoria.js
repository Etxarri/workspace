document.addEventListener('DOMContentLoaded', function() {
    const select = document.querySelector('select[name="categoria"]');
    if (select) {
        select.addEventListener('change', function() {
            this.form.submit();
        });
    }
    const ciudadSelect = document.querySelector('select[name="ciudad"]');
    if (ciudadSelect) {
        ciudadSelect.addEventListener('change', function() {
            this.form.submit();
        });
    }
});