$(function(){
    $.ajax({
        url:'/histories_api',
        success:function(data) {
            for (let i = 0; i < data.length; i++) {
                let row = data[i];
                let tr = $('<tr></tr>');
                tr.append('<td>' + row['idx'] + '</td>');
                tr.append('<td>' + row['code'] + '</td>');
                tr.append('<td>' + row['created'] + '</td>');
                $('.list tbody').append(tr);

                let backgroundColor = row['code'];
                let rgb = hexToRgb(backgroundColor);
                let textColor = getContrastColor(rgb);
                tr.css('color', textColor);
                tr.css('background-color', backgroundColor);
            }
        }
    });
});

function hexToRgb(hex) {
    let bigint = parseInt(hex.slice(1), 16);
    let r = (bigint >> 16) & 255;
    let g = (bigint >> 8) & 255;
    let b = bigint & 255;
    return [r, g, b];
}

function getContrastColor(rgb) {
    let brightness = (rgb[0] * 299 + rgb[1] * 587 + rgb[2] * 114) / 1000;
    return brightness > 128 ? 'black' : 'white';
}
