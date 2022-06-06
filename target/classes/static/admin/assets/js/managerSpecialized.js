$(document)
		.ready(
				function() {
					let descriptionEdittor= null;
					let contentEditor= null;
					(function(){
						descriptionEdittor= new FroalaEditor("#description",{
					        // Define new link styles.
					        linkStyles: {
					          class1: 'Class 1',
					          class2: 'Class 2'
					        }
					      })
					    })();
//					 (function () {
//					       contentEditor = new FroalaEditor('#content', {
//					        events: {
//					          'image.beforeUpload': function (files) {
//					            const editor = this
//					            if (files.length) {
//					              var reader = new FileReader()
//					              reader.onload = function (e) {
//					                var result = e.target.result
//					                editor.image.insert(result, null, null, editor.image.get())
//					              }
//					              reader.readAsDataURL(files[0])
//					            }
//					            return false
//					          },
////					          contentEditor.image.display ( 'inline' );
//					        }
//					      })
//					    })()
					 (function () {
						  var curentPage=   parseInt($('#curentPage').val());
						
				        window.pagObj = $('#pagination').twbsPagination({
				        	totalPages: 10,
							visiblePages: 4,
							startPage: parseInt(curentPage),
				            onPageClick: function (event, page) {
				            	if(page!=parseInt($('#curentPage').val())){
									$('#page').val(page);
									var url= window.location.pathname;
									$('#formPagination').attr('action',url)
									$('#formPagination').submit();
				            	}
				            }
				            
				        });
				    })();
					var count = 0;
					 $('#uploadSpecialized')
						.validate(
								{
									rules : {
										name : {
											required : true

										},
										code : {
											required : true

										}
									},
									messages : {
										name : {
											required : "Tên không được để trống"
										},
										code : {
											required : "Địa chỉ không được để trống"
										}
									},

									submitHandler : function(form) {
										alert('oke');
									}
								});
					$("#addSpecialized")
							.click(
									function() {
										
										if ($("#uploadSpecialized").valid()){
											var descriptionHTML= descriptionEdittor.html.get();
											var stringDesctiption=$.trim(jQuery(descriptionHTML).text());
											if(stringDesctiption){
												alert("done");
												event.preventDefault();
												var form = $('#uploadSpecialized')[0];
												var urlpath=window.location.origin;
												var data = new FormData(form);
												$
														.ajax({
															url : urlpath+"/admin/api/managerSpecialized/add",
															type : "POST",
															enctype : 'multipart/form-data',
															data : data,
															processData : false,
															contentType : false,
															cache : false,
															success : function(result) {
																if (result) {
																	success:$('#modal')
																	.modal('hide');
																	
																	complete:$('#ok').modal(
																	'show');

																	setTimeout(
																			function() {
																				window.location
																						.replace(urlpath+"/admin/managerSpecialized")
																			}, 1500);
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
																						.replace(urlpath+"/admin/managerSpecialized")
																			}, 1500);
																} else {
																	alert('Đã có lỗi xảy ra !');
																}
															}
														});
												}else{
													alert("Bạn cần nhập lại phần mô tả")
												}
											}else{
												alert("Bạn cần nhập đầy đủ thông tin cần thiết")
											}
									
										
										
									});

					// checkAllCheckBox
					$("#checkAll").change(
							function() {
								$("input[name='checkOne']").not(this).prop('checked',
										this.checked);
							});

					// checkAnyCheckBox
					var countChecked = function() {
						count = $("input[name='checkOne']:checked").length
						console.log(count);
						if (count < 1) {
							$("#btnDeleteSpecialized").prop("disabled", true);
							$("#btnEditSpecialized").prop("disabled", true);
						} else {
							$("#btnAddSpecialized").prop("disabled", true);
							$("#btnDeleteSpecialized").prop("disabled", false);
							if (count == 1) {
								$("#btnEditSpecialized").prop("disabled", false);
							} else {
								$("#btnEditSpecialized").prop("disabled", true);
							}
						}
					};
					countChecked();

					$("input[type=checkbox]").on("click", countChecked);

					// editProduct
					$("#btnEditSpecialized").click(
							function(event) {
								event.preventDefault();

								var values = new Array();
								var values2 = new Array();

								$.each($("input[name='checkOne']:checked")
										.closest("td").siblings("td"),
										function() {
											values.push($(this).text());
										});
								$.each($("input[name='checkOne']:checked")
										.closest("td").siblings("input"),
										function() {
											values2.push($(this).val());
										});
							
						
								$('#id').val($("input[name='checkOne']:checked").val());
								$('#name').val(values[0]);
//								$('#specializedId').val(values[1]).change();
								
							/*	descriptionEdittor.html.insert(values[2], true);*/
								$('#code').val(values[2])
								descriptionEdittor.html.set (values2[0]);
								$('#description').val(values2[0])
								$('#imgOld').val(values[4]);
								$("#addSpecialized").css("display", "none");
								$("#editSpecialized").css("display", "block");
								
							});
					
					$("#btnAddSpecialized").click(
							function(event) {
								event.preventDefault();
								$("#addSpecialized").css("display", "block");
								$("#editSpecialized").css("display", "none");
							});
							
							
					 $("#btnDeleteSpecialized").click(
							function(event) {
								
								event.preventDefault();
								var data = {};
								var urlpath=window.location.origin;
								var ids = $('tbody input[name="checkOne"]:checked').map(function () {
		           				return $(this).val();
		        				}).get();
								data['ids'] = ids;
									$.ajax({
									url : urlpath+"/admin/api/managerHandbok/delete",
									type : "post",
									contentType: "application/json",
									data: JSON.stringify(data),
									cache : false,
									success : function(result) {
									alert("oke lunn");
									window.location
									.replace(urlpath+"/admin/managerSpecialized");
					
					
									},
									error : function(e) {
										alert('Đã có lỗi xảy ra !');
										window.location
										.replace(urlpath+"/admin/managerSpecialized");
									}
									});
							});
							
							
							

					$("#editSpecialized")
							.click(
									function() {
										
										if ($("#uploadSpecialized").valid()){
											var descriptionHTML= descriptionEdittor.html.get();
											var stringDesctiption=$.trim(jQuery(descriptionHTML).text());
											if(stringDesctiption){
												event.preventDefault();
												var form = $('#uploadSpecialized')[0];
												var urlpath=window.location.origin;
												var data = new FormData(form);
												$
														.ajax({
															url : urlpath+"/admin/api/managerSpecialized/edit",
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
																						.replace(urlpath+"/admin/managerSpecialized")
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
																						.replace(urlpath+"/admin/managerSpecialized")
																			}, 1500);
																} else {
																	alert('Đã có lỗi xảy ra !');
																}
															}
														});
												}else{
													alert("Bạn cần nhập lại phần mô tả");
												}
											}else{
												alert("Bạn cần nhập lại các thông tin cần thiết")
											}
									});
				});