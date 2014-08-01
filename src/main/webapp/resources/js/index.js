$(function() {
	$('table.table').dataTable({});
	$("#form-crawl").submit(function() {
		return confirm("Are you sure to crawl again?");
	});
	$("table").on("click", "a.open", function() {
		$(this).attr({
			"target" : "_blank"
		});
	});
});