/**
 * Created by dell on 2017-3-16.
 */
$(document).ready(function () {
    $("a.logout").click(function (event) {
        event.preventDefault();

        $("form.logout").submit();
    });
});