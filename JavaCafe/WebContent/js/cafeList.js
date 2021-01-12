$(function() {
	$.ajax({
		url: '../getProdList',
		dataType: 'json',
		success: showContents,
		error: showErrors
	});
})
function showErrors(result) {
	console.log(result);
}
function showContents(result) {
	console.log(result);
	let data = result;
	for (val of data) { //val: data배열의 첫번재 요소를 가지고 와서 가지고 있는 각각의 필드들을 읽어오겠다.
		let elem_1, elem_2, elem_3;
		// 첫번재 자식 요소.
		elem_1 = $('<a />').attr('href', val.link);
		let e1_img = $('<img />').attr('src', '../images/' + val.image).attr('alt', val.alt);
		e1_img.addClass("card-img-top");
		$(elem_1).append(e1_img);
		// 두번째 자식 요소.
		elem_2 = $('<div />').addClass("card-body");
		let e2_h4 = $('<h4 />').addClass("card-title").append($('<a />').html(val.item).attr('href', val.link));
		let krw_price = new Intl.NumberFormat('ko-KR',{style: 'currency', currency:'KRW'}).format(val.price);
		let e2_h5 = $('<h5 />').html(krw_price);
		let e2_p = $('<p />').addClass("card-text").html(val.content);
		$(elem_2).append(e2_h4, e2_h5, e2_p);
		// 세번째 자식 요소.
		elem_3 = $('<div />').addClass("card-footer");
		let e3_small = $('<small />').addClass("text-muted");
		let star = '';
			for (let i = 0; i < val.like_it; i++) {
				star += '&#9733;';
			}

		e3_small.html(star);
		elem_3.append(e3_small);

		let div_1,
			div_2;
		div_1 = $('<div />').addClass("col-lg-4 col-md-6 mb-4");
		div_2 = $('<div />').addClass("card h-100");
		div_1.append(div_2);
		div_2.append(elem_1, elem_2, elem_3);

		$('.col-lg-9 .row').append(div_1);
	}

}