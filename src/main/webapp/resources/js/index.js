$(function() {
	$('table.table').dataTable({
		paging : false
	});
	$("#form-crawl").submit(function() {
		return confirm("Are you sure to crawl again?");
	});
});