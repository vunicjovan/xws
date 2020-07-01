<template>
	<div>
		<md-app>
			<md-app-toolbar class="md-transparent">
				<span class="md-title">{{ this.selectedTitle }}</span>
			</md-app-toolbar>

			<md-app-drawer md-permanent="full">
				<md-toolbar class="md-transparent" md-elevation="0">
					Active chat rooms
				</md-toolbar>

				<md-list>
					<div v-for="room in getChat" v-bind:key="room.senderId">
						<md-divider></md-divider>
						<md-list-item @click="setRoom(room)" class="md-button">
							<span class="md-list-item-button">{{ room.senderUsername }}</span>
						</md-list-item>
						<md-divider></md-divider>
					</div>
				</md-list>
			</md-app-drawer>

			<md-app-content>
				<div class="msgDiv" v-if="this.selectedRoom != null && this.currentMessages !== null">
					<div v-for="msg in currentMessages" v-bind:key="getMsgIndex(msg)">
						<div v-if="msg.senderId === getUser.id" style="text-align: right;">
							<md-menu md-direction="bottom-start" md-size="small" md-close-on-click class="message-card-mine">
								<span class="s" md-menu-trigger>{{ msg.content }}</span>
								<md-menu-content>
									<md-menu-item>
										<md-button @click="deleteMessage(msg)">Delete</md-button>
									</md-menu-item>
								</md-menu-content>
							</md-menu>
						</div>
						<div v-else>
							<span class="message-card">{{ msg.content }}</span>
						</div>
					</div>
				</div>

				<div v-else style="text-align: center;">
					<md-empty-state md-label="No opened conversations"></md-empty-state>
				</div>

				<md-field :class="{ 'md-invalid': $v.currentMessage.$error }" md-clearable v-if="this.currentMessages !== null">
					<label>Send message</label>
					<md-input v-on:keyup.enter="sendMessage" v-model="currentMessage" class="textara" md-autogrow maxlength="120"></md-input>
					<span class="md-error" v-if="!$v.currentMessage.required">Invalid message format</span>
				</md-field>
			</md-app-content>
		</md-app>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal, helpers } from "vuelidate/lib/validators";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);

export default {
	name: "Chat",
	mixins: [validationMixin],
	data: function() {
		return {
			selectedRoom: null,
			selectedTitle: "",
			currentMessage: "",
			currentMessages: null,
			msgCounter: 0,
			rooms: [],
			getUser: {
				id: 2,
			},
		};
	},
	computed: mapGetters(["getChat"]),
	mounted() {
		this.$store.dispatch("getMessages");

		//this.connect();
	},
	methods: {
		...mapActions(["getMessages", "sendMessage"]),

		// initializeWebSocketConnection() {
		//     // serverUrl je vrednost koju smo definisali u registerStompEndpoints() metodi na serveru
		//     let ws = new SockJS(this.serverUrl);
		//     this.stompClient = Stomp.over(ws);
		//     let that = this;

		//     this.stompClient.connect({}, function () {
		//     that.isLoaded = true;
		//     that.openGlobalSocket()
		//     });

		// },

		setRoom(room) {
			this.selectedRoom = room;
			this.selectedTitle = "Conversation with " + this.selectedRoom.senderUsername;
			this.currentMessages = this.selectedRoom.messages;
			this.msgCounter = this.currentMessages.length;
		},

		getMsgIndex(msg) {
			return this.currentMessages.indexOf(msg);
		},

		sendMessage() {
			this.$v.$touch();
			if (this.selectedTitle !== "" && !this.$v.$invalid && this.currentMessage !== "") {
				this.msgCounter = this.msgCounter + 1;
				var msg = {
					senderId: this.getUser.id,
					receiverId: this.selectedRoom.senderId,
					content: this.currentMessage,
				};
				console.log(msg);

				this.$store.dispatch("sendMessage", msg);

				this.currentMessages.push(msg);
				this.currentMessage = "";
			}
		},

		deleteMessage(message) {
			this.msgCounter = this.msgCounter - 1;
			var index = this.getMsgIndex(message)
			this.currentMessages.splice(index, 1)
			this.$store.dispatch("deleteMessage", message.id)
		}

		// send() {
		//      // send message to server
		//         if (this.stompClient && this.stompClient.connected) {
		//             this.stompClient.send("/socket-subscriber/send", JSON.stringify(msg), {});
		//         }
		// },

		// connect() {
		//     this.socket = new SockJS("http://localhost:8089/message/socket");
		//     this.stompClient = Stomp.over(this.socket);
		//     this.stompClient.connect(
		//         {},
		//         frame => {
		//             //this.connected = true;
		//             console.log(frame);
		//             this.stompClient.subscribe("/socket-publisher", tick => {
		//                 console.log(tick);
		//                 //this.received_messages.push(JSON.parse(tick.body).content);
		//             });
		//         },
		//         error => {
		//             console.log(error);
		//             //this.connected = false;
		//         }
		//     );
		// },
	},
	validations: {
		currentMessage: {
			sqli,
		},
	},
};
</script>

<style>
.md-app .md-app-drawer {
	width: 40%;
	padding: 2%;
}

.md-app {
	padding: 2%;
	height: 500px;
}

.md-app .md-list .md-list-item {
	width: 95%;
}

.md-app-content .md-field {
	position: fixed;
	bottom: 0.01%;
	width: 85%;
}

.textara {
	max-width: 90%;
}

.message-card {
	padding: 1.5%;
	border: 2.5px solid rgb(115, 153, 179);
	display: inline-block;
	margin-bottom: 2.5%;
	margin-right: 55%;
	word-break: break-word;
	border-radius: 15px;
}

.message-card-mine {
	margin-bottom: 2.5%;
	margin-left: 55%;
}

.s {
	border: 2.5px solid #1c6ea4;
	border-radius: 15px;
	padding: 1.5%;
	display: inline-block;
	word-break: break-word;
}

.md-app-content .msgDiv {
	height: 290px;
	width: 80%;
	overflow-y: auto;
	padding: 1%;
	margin: 0 auto;
}

/* width */
::-webkit-scrollbar {
	width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
	background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
	background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
	background: #555;
}
</style>
