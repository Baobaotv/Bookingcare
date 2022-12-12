$(document)
		.ready(

				function() {
					/*
					 * (function(){ new FroalaEditor("#description",{ // Define
					 * new link styles. linkStyles: { class1: 'Class 1', class2:
					 * 'Class 2' } }) })() shortDescription
					 */
					(function() {
						const editorInstance = new FroalaEditor(
								'#description',
								{
									events : {
										'image.beforeUpload' : function(files) {
											const editor = this
											if (files.length) {
												var reader = new FileReader()
												reader.onload = function(e) {
													var result = e.target.result
													editor.image.insert(result,
															null, null,
															editor.image.get())
												}
												reader.readAsDataURL(files[0])
											}
											return false
										}
									}
								})
					})();
					(function() {
						const editorshortDescription = new FroalaEditor(
								'#shortDescription',
								{
									events : {
										'image.beforeUpload' : function(files) {
											const editor = this
											if (files.length) {
												var reader = new FileReader()
												reader.onload = function(e) {
													var result = e.target.result
													editor.image.insert(result,
															null, null,
															editor.image.get())
												}
												reader.readAsDataURL(files[0])
											}
											return false
										}
									}
								})
					})();
					
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
					
					$("#img").change(function(event) {
						if (this.files && this.files[0]) {
							var reader = new FileReader();
							reader.onload = function(e) {
								$('#showImage').attr('src', reader.result);
							}
							reader.readAsDataURL(this.files[0]);
						}
					});
					var count = 0;

					$("#addUser")
							.click(
									function() {

										if ($("#uploadUser").valid()) {
											event.preventDefault();

											// Get form
											var form = $('#uploadUser')[0];
											var urlLocation = window.location.origin;
											var urlpath = urlLocation.substring(0, urlLocation.indexOf('?') != -1 ? urlLocation.indexOf('?') : urlLocation.length);
											var data = new FormData(form);
											$
													.ajax({
														url : urlpath
																+ "/admin/api/managerUser/add",
														type : "POST",
														enctype : 'multipart/form-data',
														data : data,
														processData : false,
														contentType : false,
														cache : false,
														success : function(
																result) {
															if (result) {
																success: $(
																		'#modal')
																		.modal(
																				'hide');

																complete: $(
																		'#ok')
																		.modal(
																				'show');

																setTimeout(
																		function() {
																			window.location
																					.replace(urlpath
																							+ "/admin/managerUser")
																		}, 1500);
															}
														},
														error : function(e) {
															if (e.status) {
																success: $(
																		'#modal')
																		.modal(
																				'hide');

																complete: $(
																		'#ok')
																		.modal(
																				'show');

																setTimeout(
																		function() {
																			window.location
																					.replace(urlpath
																							+ "/admin/managerUser")
																		}, 1500);
															} else {
																alert('Đã có lỗi xảy ra !');
															}
														}
													});
										} else {
											alert('Bạn cần điền lại thông tin')
										}
									});
					$('#uploadUser')
							.validate(
									{
										rules : {
											fullname : {
												required : true

											},
											username : {
												required : true

											},
											phoneNumber : {
												required : true,
												number : true,
												minlength : 10,
												maxlength : 10
											},
											location : {
												required : true
											},
											specializedId : {
												required : true
											},
											workTimeId : {
												required : true
											},
											roleName : {
												required : true
											},
											email : {
												required : true,
												email : true
											},
											hospitalId : {
												required : true
											},
											sex : {
												required : true
											},
											yearOfBirth : {
												required : true
											},
											password : {
												required : true
											},
											confirmPassword : {
												equalTo : "#password"
											}
										},
										messages : {
											fullname : {
												required : "Tên không được để trống"
											},
											username : {
												required : "Tài khoản không được để trống"
											},
											roleName : {
												required : "Chức năng không được để trống"
											},
											phoneNumber : {
												required : "Số điện thoại không được để trống ",
												number : "Chỉ được phép nhập chữ số"
											},
											email : {
												required : "Email không được để trống",
												email : "Bạn đã nhập sai định dạng email"
											},
											hospitalId : {
												required : "Bệnh viện không được để trống"
											},
											workTimeId : {
												required : "Bạn chưa chọn ca khám",

											},
											location : {
												required : "Bạn chưa chọn địa chỉ",
											},
											sex : {
												required : "Giới tính không được để trống "
											},
											yearOfBirth : {
												required : "Ngày sinh không được để trống "
											},
											specializedId : {
												required : "Chuyên khoa không được để trống "
											},
											password : {
												required : "Mật khẩu không được để trống"
											},
											confirmPassword : {
												equalTo : "Mật khẩu không trùng khớp"
											},
										},

										submitHandler : function(form) {
											alert('oke');
										}
									});

					// checkAllCheckBox
					$("#checkAll").change(
							function() {
								$("input[name='checkOne']").not(this).prop(
										'checked', this.checked);
							});
					$("#roleName").change(function() {
						var value = $("#roleName").val();
						if (value != "ROLE_DOCTOR") {
							// alert("hi");
							$("#hospitalId").hide();
							$(".checkWorkTime").hide();
							$("#specializedId").hide();
							if (value == "ROLE_USER") {
								// $('#description').hide();
								$('#img').hide();
							}
						} else {
							$("#hospitalId").show();
							$(".checkWorkTime").show();
							$("#specializedId").show();
							// $('#description').show();
							$('#img').show();
						}

					});

					// checkAnyCheckBox
					var countChecked = function() {
						count = $("input[name='checkOne']:checked").length
						console.log(count);
						if (count < 1) {
							$("#btnAddUser").prop("disabled", false);
							$("#btnDelete").prop("disabled", true);
							$("#btnEdit").prop("disabled", true);
						} else {
							$("#btnAddUser").prop("disabled", true);
							$("#btnDelete").prop("disabled", false);
							if (count == 1) {
								$("#btnEdit").prop("disabled", false);
							} else {
								$("#btnEdit").prop("disabled", true);
							}
						}
					};
					
					var checkUsername= function(){
						$("#username").on("input", function(e) {
							$('#msg').hide();
							if ($('#username').val() == null || $('#username').val() == "") {
								$('#msg').show();
								$("#msg").html("Username is a required field.").css("color", "red");
								$('#addUser').hide();
							} else {
								$.ajax({
									type: 'post',
									url: "/user",
									data: JSON.stringify({username: $('#username').val()}),
									contentType: 'application/json; charset=utf-8',
									cache: false,
									beforeSend: function (f) {
										$('#msg').show();
										$('#msg').html('Checking...');
									},
									statusCode: {
									    500: function(xhr) {
									    	$('#msg').show();
									    	$("#msg").html("Username available").css("color", "green");
									    	$('#addUser').show();
									    }
									},
									success: function(msg) {
										$('#msg').show();
										if(msg !== null || msg !== "") {
											$("#msg").html("Username already taken").css("color", "red");
											$('#addUser').hide();
										} else {
											$("#msg").html("Username available").css("color", "green");
											$('#addUser').show();
										}
									},
									error: function(jqXHR, textStatus, errorThrown) {
										$('#msg').show();
										$("#msg").html(textStatus + " " + errorThrown).css("color", "red");
									}
								});
							}
						});
					}
					countChecked();

					$("input[type=checkbox]").on("click", countChecked);

					// editProduct
					$("#btnEdit").click(
							function(event) {
								event.preventDefault();

								var values = new Array();

								$.each($("input[name='checkOne']:checked")
										.closest("td").siblings("td"),
										function() {
											values.push($(this).text());
										});
								console.log(values);
								$('#id').val(values[0]);
								$('#categoryId').val(values[1]).change();
								$('#sizeId').val(values[2]).change();
								$('#nameProduct').val(values[3]);

								$('#codeProduct').val(values[4]);
								$('#image').val(values[5]);
								$('#price').val(values[6]);
								$('#amount').val(values[7]);
								$('#memo').val(values[8]);

								$("#addProduct").css("display", "none");
								$("#editProduct").css("display", "block");
							});

					$("#btnAddUser").click(function(event) {
						event.preventDefault();
						$("#addUser").css("display", "block");
						$("#editUser").css("display", "none");
						checkUsername();
						
					});

					$("#btnDelete").click(
							function(event) {
								event.preventDefault();
								var data = {};
								var urlpath = window.location.origin;
								var urlpath = urlLocation.substring(0, urlLocation.indexOf('?') != -1 ? urlLocation.indexOf('?') : urlLocation.length);
								var ids = $(
										'tbody input[name="checkOne"]:checked')
										.map(function() {
											return $(this).val();
										}).get();
								data['ids'] = ids;
								$.ajax({
									url : urlpath
											+ "/admin/api/managerUser/delete",
									type : "post",
									contentType : "application/json",
									data : JSON.stringify(data),
									cache : false,
									success : function(result) {
										alert("oke lunn");
										window.location.replace(urlpath
												+ "/admin/managerUser");

									},
									error : function(e) {
										alert('Đã có lỗi xảy ra !');
										window.location.replace(urlpath
												+ "/admin/managerUser");
									}
								});
							});

//					$("#editProduct")
//							.click(
//									function() {
//										event.preventDefault();
//
//										// Get form
//										var form = $('#fileUploadForm')[0];
//
//										var data = new FormData(form);
//										$
//												.ajax({
//													url : "/baitaplon/quan-tri/san-pham/sua-san-pham",
//													type : "post",
//													enctype : 'multipart/form-data',
//													data : data,
//													processData : false,
//													contentType : false,
//													cache : false,
//													success : function(result) {
//														if (result) {
//
//															$('#modal').modal(
//																	'toggle');
//
//															setTimeout(
//																	function() {
//																		window.location
//																				.replace("http://localhost:8080/baitaplon/quan-tri/san-pham")
//																	}, 1000);
//															$('#ok').modal(
//																	'show');
//
//														}
//													},
//													error : function(e) {
//														if (e.status) {
//															success: $('#modal')
//																	.modal(
//																			'hide');
//
//															complete: $('#ok')
//																	.modal(
//																			'show');
//
//															setTimeout(
//																	function() {
//																		window.location
//																				.replace("http://localhost:8080/baitaplon/quan-tri/san-pham")
//																	}, 1500);
//														} else {
//															alert('Đã có lỗi xảy ra !');
//														}
//													}
//												});
//									});
				});