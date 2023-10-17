function decimalToHex(d, padding) {
  var hex = Number(d).toString(16);
  padding = typeof (padding) === "undefined" || padding === null ? padding = 2 : padding;
  while (hex.length < padding) {
    hex = "0" + hex;
  }
  return hex;
}

$(function(){
  var li_elements = $('.content li');
  for (var i = 0; i < li_elements.length; i++) {
    var red = Math.floor(Math.random() * 256);
    var green = Math.floor(Math.random() * 256);
    var blue = Math.floor(Math.random() * 256);
    li_elements.eq(i).css({'background-color': 'rgb(' + red + ',' + green + ',' + blue + ')'});
    li_elements.eq(i).html('<span class="code">#' + decimalToHex(red) + decimalToHex(green) + decimalToHex(blue) + '</span><br />' + '(' + red + ',' + green + ',' + blue + ')');
  }
});

$(document).on('click', '.content li', function(event){
  console.log($(this).find('.code').text());
  $.ajax({
    url:'/select_color',
    data: {'code': $(this).find('.code').text()},
    success:function(data) {
    	location.href = '/list';
    }
  });
});
