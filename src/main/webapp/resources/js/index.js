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
	$("table").on("click", "a.link-auction-count", function() {
		function success(data) {
			var dialog = $("#dialog-info");
			var container = $(".container-fluid", dialog);
			container.empty();
			var count = data.length;
			var n;
			var template = $(".row-template", dialog);
			for (n = 0; n < count - 1; n++) {
				var row = template.clone();
				row.removeClass("row-template");
				$(".col-date", row).text(data[n].date);
				$(".col-price", row).text(data[n].price);
				container.append(row);
			}
			dialog.dialog("open");
		}

		var span = $("span.link-id", $(this));
		var id = span.text();
		var form = $("#form-auction-history");
		$("input.value", form).val(id);
		var options = {
			"success" : success
		};
		form.ajaxSubmit(options);
		return false;
	});

	$("#dialog-info").dialog({
		"autoOpen" : false,
		"buttons" : [ {
			text : "Ok",
			click : function() {
				$(this).dialog("close");
			}
		} ]
	});

});