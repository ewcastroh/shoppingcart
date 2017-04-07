/**
 * Created by z00382545 on 4/6/17.
 */

$( document ).ready(function() {
    var total = 0;

    $('.item-qty').on('input', function() {
        total = 0;

        var id = this.id.substring(4);
        var qty = this.value;
        var price = $('#price-'+id).text();

        $('#subtotal-'+id).text((price*qty).toFixed(2));

        $('.subtotal').each(function(){
            total=total + parseFloat($(this).text());
        });

        $('#total').text(total);
    });

    $('.subtotal').each(function(){
        total=total + parseFloat($(this).text());
    });

    $('#total').text(total);
});