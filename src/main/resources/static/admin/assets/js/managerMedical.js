$(document)
		.ready(
				
				


				function() {
					let descriptionEdittor= null;
					let contentEditor= null;
					var count = 0;
					var inforMedical;
					$("#btnComplete")
							.click(
									function(event) {
										event.preventDefault();
										var data = {};
										var urlpath=window.location.href;
										var ids = $('tbody input[name="checkOne"]:checked').map(function () {
				           				return $(this).val();
				        				}).get();
										data['ids'] = ids;
										$.ajax({
										url : urlpath+"/complete",
										type : "post",
										contentType: "application/json",
										data: JSON.stringify(data),
										cache : false,
										success : function(result) {
										alert("oke lunn");
										window.location
										.replace(urlpath);
										},
										error : function(e) {
											alert('Đã có lỗi xảy ra !');
											window.location
											.replace(urlpath);
										}
										});
										
									});

					// checkAllCheckBox
					$("#checkAll").change(
							function() {
								$("input[name='checkOne']").not(this).prop('checked',
										this.checked);
								countChecked();
							});

					// checkAnyCheckBox
					var countChecked = function() {
						count = $("input[name='checkOne']:checked").length
						console.log(count);
						if (count < 1) {
							$("#btnDeleteMedical").prop("disabled", true);
							$("#btnInforMedia").prop("disabled", true);
							$("#btnSendMedicalRecords").prop("disabled", true);
							$("#btnComplete").prop("disabled", true);
							$("#btnUpdateTimeClose").prop("disabled", true);
						} else {
							
							$("#btnComplete").prop("disabled", false);
							$("#btnDeleteMedical").prop("disabled", false);
							if (count == 1) {
								$("#btnInforMedia").prop("disabled", false);
								$("#btnSendMedicalRecords").prop("disabled", false);
								$("#btnUpdateTimeClose").prop("disabled", false);

							} else {
								$("#btnInforMedia").prop("disabled", true);
								$("#btnSendMedicalRecords").prop("disabled", true);
								$("#btnUpdateTimeClose").prop("disabled", true);
							}
						}
					};
					countChecked();

					$("input[type=checkbox]").on("click", countChecked);

					// showMedical
					$("#btnInforMedia").click(
							function(event) {
								event.preventDefault();

								var values = new Array();
								

								$.each($("input[name='checkOne']:checked")
										.closest("td").siblings("td"),
										function() {
											values.push($(this).text());
										});
								$('#id').val($("input[name='checkOne']:checked").val());
								 $("#namePatient").text(values[2]);
								 $("#phonePatient").text(values[3]);
								 $("#nameScheduler").text(values[6]);
								 $("#phoneScheduer").text(values[5]);
								 $("#yearOfBirth").text(values[10]);
								 $("#sex").text(values[7]);
								 $("#location").text(values[8]);
								 $("#reason").text(values[9]);
								 $("#doctorName").text(values[0]);
								 $("#wordTimeTime").text(values[1]);
								 $("#date").text(values[4]);
								 if(values[11]=='on'){
									 $('#type').text("Tư vấn online");
								 }else{
									 $('#type').text("Khám tại cơ sở");
								 }
								 $("#hospitalName").text(values[12])
								 
							});
					// btnSendMedicalRecords
					$("#btnSendMedicalRecords").click(
						function(event) {
							event.preventDefault();
							// var values = new Array();
							//
							// $.each($("input[name='checkOne']:checked")
							// 		.closest("td").siblings("td"),
							// 	function() {
							// 		values.push($(this).text());
							// 	});
							$('#medicalId').val($("input[name='checkOne']:checked").val());
						});
					//submit sendRecords

					$("#sendMedical").click(
						function(event) {
							event.preventDefault();
							var form = $('#uploadMedicalRecords')[0];
							var urlpath = window.location.href;
							var data = new FormData(form);
							$.ajax({
								url : urlpath+"/uploadMedicalRecords",
								type : "POST",
								enctype : 'multipart/form-data',
								data : data,
								processData : false,
								contentType : false,
								cache : false,
								success : function(result) {
									alert("Gửi thành công");
									window.location
										.replace(urlpath+"/admin/managerMedicalComplete");

								},
								error : function(e) {
									alert('Đã có lỗi xảy ra !');
									window.location
										.replace(urlpath+"/admin/managerMedicalComplete");
								}
							});
						});

					// show changeTimeCloseMedical
					$("#btnUpdateTimeClose").click(
						function(event) {
							event.preventDefault();

							var values = new Array();


							$.each($("input[name='checkOne']:checked")
									.closest("td").siblings("td"),
								function() {
									values.push($(this).text());
								});
							$('#idMedicalChange').val($("input[name='checkOne']:checked").val());
							$("#namePatientTime").text(values[2]);
							// $("#phonePatient").text(values[3]);
							// $("#nameScheduler").text(values[6]);
							// $("#phoneScheduer").text(values[5]);
							// $("#yearOfBirth").text(values[10]);
							// $("#sex").text(values[7]);
							// $("#location").text(values[8]);
							// $("#reason").text(values[9]);
							// $("#doctorName").text(values[0]);
							$("#wordTimeTimeOld").text(values[1]);
							// $("#date").text(values[4]);
							// if(values[11]=='on'){
							// 	$('#type').text("Tư vấn online");
							// }else{
							// 	$('#type').text("Khám tại cơ sở");
							// }
							// $("#hospitalName").text(values[12])

						});

					// event changeTimeCloseMedical
					$("#changeTime").click(
						function(event) {
							event.preventDefault();
							var idMedicalChange = $("#idMedicalChange").val();
							var idWkChange = $("input[name='workTimeId']:checked").val();
							var values = {idWk:idWkChange, idMedical:idMedicalChange};
							var urlpath=window.location.origin;
							$.ajax({
								url: '/api/media/change-time-close',
								type: "post",
								data: JSON.stringify(values),
								dataType: 'json',
								contentType: "application/json",
								cache: false,
								success: function(result) {

									window.location.replace(urlpath+"/admin/managerMedical")
									alert('Thay đổi thành công');
								},
								error: function(error) {
									window.location.replace(urlpath+"/admin/managerMedical")
									alert('Đã có lỗi xảy ra ! Bạn chưa chọn thời gian (hoặc thời gian không thoả mãn)');
								}
							});

						});
					
					$("#btnAddMedical").click(
							function(event) {
								event.preventDefault();
								$("#addMedical").css("display", "block");
								$("#editMedical").css("display", "none");
							});
							
							
					 $("#btnDeleteMedical").click(
							function(event) {
								event.preventDefault();
								var data = {};
								var urlpath=window.location.origin;
								var ids = $('tbody input[name="checkOne"]:checked').map(function () {
		           				return $(this).val();
		        				}).get();
								data['ids'] = ids;
									$.ajax({
									url : urlpath+"/api/media/deletes",
									type : "delete",
									contentType: "application/json",
									data: JSON.stringify(ids),
									cache : false,
									success : function(result) {
									alert("Đã xoá thành công");
									window.location
									.replace(urlpath+"/admin/managerMedical");
					
					
									},
									error : function(e) {
										alert('Đã có lỗi xảy ra !');
										window.location
										.replace(urlpath+"/admin/managerMedical");
									}
									});
							});
							
							
							

					$("#editMedical")
							.click(
									function() {
										event.preventDefault();
										var form = $('#uploadMedical')[0];
										var urlpath=window.location.href;
										var data = new FormData(form);
										$
												.ajax({
													url : urlpath+"/edit",
													type : "post",
													enctype : 'multipart/form-data',
													data : data,
													processData : false,
													contentType : false,
													cache : false,
													success : function(result) {
														if (result) {
														
														
															$('#modal').modal('toggle');

															setTimeout(
																	function() {
																		window.location
																				.replace(urlpath)
																	}, 1000);
																	$('#ok').modal('show');
															
														}
													},
													error : function(e) {
														if (e.status) {
															success:$('#modal')
															.modal('hide');
															
															complete:$('#ok').modal(
															'show');

															setTimeout(
																	function() {
																		window.location
																				.replace(urlpath+"/admin/managerMedical")
																	}, 1500);
														} else {
															alert('Đã có lỗi xảy ra !');
														}
													}
												});
									});
				});