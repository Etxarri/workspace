document.addEventListener('DOMContentLoaded', function() {
    const select = document.querySelector('select[name="categoria"]');
    if (select) {
        select.addEventListener('change', function() {
            this.form.submit();
        });
    }
});