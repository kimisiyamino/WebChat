<%@ page import="com.eleonoralion.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
	<title>Chat</title>

		<!-- orig -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.js"></script>
		
		<!------ Include the above in your HEAD tag ---------->
		<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
		<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

		<!-- CSS & JS -->
		<link rel="stylesheet" type="text/css" href="chat.css">

		<!-- Открытие меню юзера -->
		<script language="JavaScript">
			$(document).ready(function(){
				$('#action_menu_btn').click(function(){
					$('.action_menu').toggle();
				});
			});
		</script>

		<!-- Веб сокет соединение -->
		<script type="text/javascript">

			let wsUrl;
			if (window.location.protocol === 'http:') {
				wsUrl = 'ws://';
			} else {
				wsUrl = 'wss://';
			}
			const ws = new WebSocket(wsUrl + window.location.host + "/chat/" + "<%=session.getAttribute("nickname")%>");

			ws.onmessage = function(event) {
				// Тут HTML код чпанели чата
				const chatPanel = document.getElementById("chat");
				// Парсим message
				const message = JSON.parse(event.data);

				// Тут еще реализовать логику с автаром
				let send;
				if(message.nick === ("<%=session.getAttribute("nickname")%>")){
					send = "<div class=\"d-flex justify-content-end mb-4\"><div class=\"msg_cotainer_send\">" + message.message + "<span class=\"msg_time_send\">" + message.time + "</br>" + message.nick + "</span></div><div class=\"img_cont_msg\"><img src=\""+ message.userImagePath + "\" class=\"rounded-circle user_img_msg\"></div></div>";
				}else if(message.nick === "notification"){
					send = "<div class=\"d-flex justify-content-center mb-4\"><div class=\"msg_cotainer\">" + message.message + "<span class=\"msg_time_send\">" + message.time + "</br>" + "</div></div>";
				}else{
					send = "<div class=\"d-flex justify-content-start mb-4\"><div class=\"img_cont_msg\"><img src=\""+ message.userImagePath + "\" class=\"rounded-circle user_img_msg\" alt=\"\"></div><div class=\"msg_cotainer\">" + message.message + "<span class=\"msg_time\">" + message.time + "</br>" + message.nick + "</span></div></div>";
				}

				chatPanel.innerHTML+=send;
			};

			ws.onerror = function(event){
				console.log("Error ", event)
			}

			function sendMsg() {
				const msg = document.getElementById("msg").value;
				if(msg)
				{
					ws.send(msg);
				}
				document.getElementById("msg").value="";
			}
		</script>

	</head>
	
	<!--Coded With Love By Mutiullah Samim-->
	<!--Edited With Love By Eleonora Lion-->
	<body>
		<!-- Все блоки -->
		<div class="container-fluid h-100">
			<div class="row justify-content-center h-100">
				
				<!-- Левый блок навигации -->
				<div class="col-md-4 col-xl-3 chat">
					<div class="card mb-sm-3 mb-md-0 contacts_card">
						
						<!-- Панель юзера -->
						<div class="card-header msg_head">
							
							<!-- Карточка с аватаром и никнеймом юзера -->
							<div class="d-flex bd-highlight">
								
								<div class="img_cont">
									<img src="<%=session.getAttribute("userImagePath")%>" class="rounded-circle user_img">
								</div>
								<div class="user_info">
									<span><%=session.getAttribute("nickname")%></span>
									<p><%=session.getAttribute("nickname")%>, 0</p>
								</div>
							</div>
							
							<!-- Кнопка -->
							<span id="action_menu_btn"><i class="fas fa-ellipsis-v"></i></span>
							<div class="action_menu">
								<ul>
									<li><i class="fas fa-user-circle"></i> Edit profile</li>
						
									<li><i class="fas fa-ban"></i> Logout</li>
								</ul>
							</div>
							
						</div>
						
						<!-- Панель поиск -->
						<div class="card-header">
							<div class="input-group">
								<input type="text" placeholder="Search..." name="" class="form-control search">
								<div class="input-group-prepend">
									<span class="input-group-text search_btn"><i class="fas fa-search"></i></span>
								</div>
							</div>
						</div>
						
						<!-- Панель участников -->
						<div class="card-body contacts_body">
							<ui class="contacts">
								<!-- Участники -->
								
								<li>
									<div class="d-flex bd-highlight">
										<div class="img_cont">
											<img src="https://static.turbosquid.com/Preview/001214/650/2V/boy-cartoon-3D-model_D.jpg" class="rounded-circle user_img">
										</div>
										<div class="user_info">
											<span>Rashid Samim</span>
											<p>Rashid left 50 mins ago</p>
										</div>
									</div>
								</li>
							</ui>
						</div>
						
						<div class="card-footer"></div>
						
					</div>
				</div><!-- </Левый блок навигации -->
				
				<!-- Правый блок чата -->
				<div class="col-md-8 col-xl-6 chat">
					<div class="card">
						
						<!-- Панель чата -->
						<div id="chat" class="card-body msg_card_body">

							
						</div>
						
						<!-- Нижняя панель ввода сообщения -->
						<div class="card-footer">
							
							<div class="input-group">
								<!-- Кнопка прикрепить -->
								<div class="input-group-append">
									<span class="input-group-text attach_btn"><i class="fas fa-paperclip"></i></span>
								</div>
								<!-- Поле для ввода текста -->

								<input name="msg" class="form-control type_msg" id="msg" placeholder="Type your message..."/>

								<!-- Кнопка отправить -->
								<div class="input-group-append">
									<span onclick="return sendMsg();" class="input-group-text send_btn"><i class="fas fa-location-arrow"></i></span>
								</div>
							</div>
							
						</div>
						
						
					</div>	<!-- </Правый блок чата -->
				</div>
				
				
			</div> <!--  </Все блоки -->
		</div>
		
	</body>
</html>