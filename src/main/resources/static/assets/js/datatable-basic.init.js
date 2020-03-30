$(document).ready(function() {
	
	$("#yes-btn").click(function(){
		$("#pass").removeAttr("disabled");
		$("#conf").removeAttr("disabled");
	});
	
	$("#no-btn").attr("checked", "checked");
	$("#no-btn").click(function(){
		$("#pass").attr("disabled","disabled");
		$("#conf").attr("disabled","disabled");
	});
	/** ********************************************************************************** */

	/** ********************************************************************************** */

	/***************************************************************************
	 * Basic Table *
	 **************************************************************************/
	$('#datatable-responsive1').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Charges Record',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'Charges Record',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'Charges Record',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		}, {
			extend : 'print',
			title : 'Charges Record',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		} ],
		responsive : false
	});
	
	$('#datatable-responsive6').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Charges Record',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1, 2, 3 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'Charges Record',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1, 2,3 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'Charges Record',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2,3 ]
			}
		}, {
			extend : 'print',
			title : 'Charges Record',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2,3 ]
			}
		} ],
		responsive : false
	});
	
	$('#datatable-responsive7').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Payment Record',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1, 2, 3 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'Payment Record',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1, 2,3 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'Payment Record',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2,3 ]
			}
		}, {
			extend : 'print',
			title : 'Payment Record',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2,3 ]
			}
		} ],
		responsive : false
	});
	
	
	
	$('#datatable-responsive4').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Room Types Register',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'Room Types Register',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'Room Types Register',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		}, {
			extend : 'print',
			title : 'Room Types Register',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2 ]
			}
		} ],
		responsive : false
	});
	
	$('#datatable-responsive5').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'User Register',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'User Register',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'User Register',
			header : false,
			exportOptions : {
				columns : [ 0]
			}
		}, {
			extend : 'print',
			title : 'User Register',
			header : false,
			exportOptions : {
				columns : [ 0, 1 ]
			}
		} ],
		responsive : false
	});
	
	$('#datatable-responsive8').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'User Register',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'User Register',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0]
			}
		}, {
			extend : 'csvHtml5',
			title : 'User Register',
			header : false,
			exportOptions : {
				columns : [ 0]
			}
		}, {
			extend : 'print',
			title : 'User Register',
			header : false,
			exportOptions : {
				columns : [ 0]
			}
		} ],
		responsive : false
	});
	
	$('#datatable-responsive2').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Room register',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'Room register',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'Room register',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6 ]
			}
		}, {
			extend : 'print',
			title : 'Room register',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6 ]
			}
		} ],
		responsive : false
	});
	
	$('#datatable-responsive3').DataTable({
		dom : 'Bfrtip',
		buttons : [ {
			extend : 'excelHtml5',
			title : 'Registration records',
			className : "data-buttons",
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6, 7 ]
			}
		}, {
			extend : 'pdfHtml5',
			title : 'Registration records',
			// messageTop: 'Passengers List',
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6, 7 ]
			}
		}, {
			extend : 'csvHtml5',
			title : 'Registration records',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6, 7 ]
			}
		}, {
			extend : 'print',
			title : 'Registration records',
			header : false,
			exportOptions : {
				columns : [ 0, 1, 2, 3, 4, 5, 6, 7 ]
			}
		} ],
		responsive : false
	});

	$('.select2').select2();
	
	$(".form_datetime").datetimepicker({format: 'yyyy-mm-dd hh:ii'});
	
	

	/***************************************************************************
	 * Default Order Table *
	 **************************************************************************/
	$('#default_order').DataTable({
		responsive : true,
		"order" : [ [ 3, "desc" ] ]
	});

	/***************************************************************************
	 * Multi-column Order Table *
	 **************************************************************************/
	$('#multi_col_order').DataTable({
		columnDefs : [ {
			targets : [ 0 ],
			orderData : [ 0, 1 ]
		}, {
			targets : [ 1 ],
			orderData : [ 1, 0 ]
		}, {
			targets : [ 4 ],
			orderData : [ 4, 0 ]
		} ]
	});
});