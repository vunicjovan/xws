<template>
	<div v-if="isLogged && getUser !== null && (getUser.roles.includes('AGENT') || getUser.roles.includes('SIMPLE_USER'))">
		<md-app>
			<md-app-toolbar class="md-transparent">
				<span class="md-title my-title" v-if="this.selectedTitle !== ''">
					<i class="fa fa-circle" aria-hidden="true" style="color:#1fb807;"></i>
					{{ this.selectedTitle }}
				</span>
			</md-app-toolbar>

			<md-app-drawer md-permanent="full">
				<md-toolbar class="my-toolbar md-transparent" md-elevation="0">
					R E N T A S O U L
					<br />C h a t b o x
				</md-toolbar>

				<md-list>
					<div v-for="room in getChat" v-bind:key="room.senderId">
						<md-divider></md-divider>
						<md-list-item @click="setRoom(room)" class="md-button roomDiv">
							<span class="md-list-item-button my-room">
								<i class="fa fa-circle" aria-hidden="true" style="color:#1fb807;"></i>
								{{ room.senderUsername }}
							</span>
						</md-list-item>
						<md-divider></md-divider>
					</div>
				</md-list>
			</md-app-drawer>

			<md-app-content>
				<div v-chat-scroll="{ smooth: true }" ref="msgd" class="msgDiv" v-if="this.selectedRoom != null && this.currentMessages !== null">
					<div v-for="msg in currentMessages" v-bind:key="getMsgIndex(msg)">
						<div v-if="msg.senderId === getUser.id" style="text-align: right;" @mouseenter="showDelete = msg.id" @mouseleave="showDelete = null">
							<md-button class="delete-button-mine md-icon-button" v-show="showDelete === msg.id" @click="deleteMessage(msg)"
								><md-icon class="fas fa-trash"
							/></md-button>
							<span class="message-card-mine">
								{{ msg.content }}
							</span>
						</div>
						<div v-else>
							<span class="message-card">
								{{ msg.content }}
							</span>
						</div>
					</div>
				</div>

				<div v-else style="text-align: center;">
					<md-empty-state md-label="No opened conversations"></md-empty-state>
				</div>

				<md-field :class="{ 'md-invalid': $v.currentMessage.$error }" md-clearable v-if="this.currentMessages !== null">
					<label>Send message</label>
					<md-input v-on:keyup.enter="sendMessage" v-model="currentMessage" class="textara" md-autogrow maxlength="120"></md-input>
					<span class="md-error" v-if="!$v.currentMessage.sqli">Invalid message format</span>
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
			currentMessages: [],
			msgCounter: 0,
			rooms: [],
			showDelete: null,
		};
	},
	computed: mapGetters(["isLogged", "getUser", "getChat"]),
	mounted() {
		if (this.getUser) {
			this.$store.dispatch("getMessages", this.getUser.id);
		}
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

			/*if (this.$refs.msgd !== undefined) {
                this.$refs.msgd.scrollTop = this.$refs.msgd.lastElementChild.offsetTop;
            }*/
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
					username: this.getUser.firstName + " " + this.getUser.lastName,
				};

				this.$store
					.dispatch("sendMessage", msg)
					.then((data) => {
						this.currentMessages.push(data);
					})
					.catch((error) => console.log(error));

				this.currentMessage = "";
			}
		},

		deleteMessage(message) {
			this.$store
				.dispatch("deleteMessage", message)
				.then((data) => {
					const index = this.currentMessages.findIndex((msg) => msg.id === message.id);
					if (index != -1) this.currentMessages.splice(index, 1);
				})
				.catch((error) => console.log(error));
		},

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
	watch: {
		getUser: {
			handler(val) {
				if (val) {
					this.$store.dispatch("getMessages", this.getUser.id);
				}
			},
		},
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
	width: 95%;
}

.textara {
	max-width: 95%;
}

.message-card {
	padding: 1.5%;
	border: 2.5px solid rgb(115, 153, 179);
	display: inline-block;
	margin-bottom: 2.5%;
	margin-right: 55%;
	word-break: break-word;
	border-radius: 15px;
	background: rgb(115, 153, 179);
	user-select: none;
}

.message-card-mine {
	padding: 1.5%;
	border: 2.5px solid #1c6ea4;
	display: inline-block;
	margin-bottom: 2.5%;
	margin-left: 0%;
	word-break: break-word;
	border-radius: 15px;
	background: #1c6ea4;
	user-select: none;
}

.delete-button-mine {
	margin-right: 55%;
}

.md-app-content .msgDiv {
	height: 305px;
	width: 100%;
	overflow-y: auto;
	padding: 1%;
	margin: 0 auto;
	background: #474747;
	border: 0.5px solid #3f3f3f;
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

.roomDiv {
	background: linear-gradient(to bottom, #7892c2 5%, #476e9e 100%);
	background-color: #7892c2;
	border-radius: 6px;
	border: 1px solid #4e6096;
	display: inline-block;
	cursor: pointer;
	color: #ffffff;
	font-family: Arial;
	font-size: 15px;
	font-weight: bold;
	padding: 6px 24px;
	text-decoration: none;
	text-shadow: 0px 1px 0px #283966;
	align-content: left;
}

.message-card:hover {
	border: 2.5px solid transparent;
	background: linear-gradient(to bottom, #1c6ea4 5%, #1c6ea4 100%);
	background-color: rgb(115, 153, 179);
}

.message-card-mine:hover {
	border: 2.5px solid transparent;
	background: linear-gradient(to top, rgb(115, 153, 179) 5%, rgb(115, 153, 179) 100%);
	background-color: rgb(115, 153, 179);
}

.message-card-mine-wrapper:hover + .delete-button-mine {
	display: inline-block !important;
}

.roomDiv:hover {
	background: linear-gradient(to bottom, #476e9e 5%, #1c6ea4 100%);
	background-color: #1c6ea4;
}
.roomDiv:active {
	position: relative;
	top: 1px;
}

.my-toolbar,
.my-title,
.my-room {
	font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande", "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
	font-weight: bold;
	user-select: none;
}

.my-par {
	font-size: 70%;
}
</style>
