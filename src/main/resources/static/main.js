// /**
//  * Created by z00382545 on 4/6/17.
//  */
//
$( document ).ready(function() {

    $('.item-qty').on('input', function() {

        var id = this.id.substring(4);

        $('#update-'+id).css("display", "inline-block");



    });
//
//     $('.subtotal').each(function(){
//         total=total + parseFloat($(this).text());
//     });
//
//     $('#total').text(total);
//     $('#hidden-total').val(total);
});