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
                    <div v-for="room in rooms" v-bind:key="room.id">
                        <md-divider></md-divider>
                        <md-list-item @click="setRoom(room)" class="md-button">
                            <span class="md-list-item-button">{{ room.name }}</span>
                        </md-list-item>
                        <md-divider></md-divider>
                    </div>
                </md-list>
            </md-app-drawer>
            
            <md-app-content>
                <div class="msgDiv" v-if="this.selectedRoom != null && this.currentMessages !== null">
                    <div v-for="msg in currentMessages" v-bind:key="getMsgIndex(msg)">
                        <div v-if="msg.senderID === 2" style="text-align: right;">
                            <span class="message-card-mine">{{ msg.content }}</span>
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
import { mapGetters } from "vuex";
import { validationMixin } from "vuelidate";
import { required, integer, decimal, helpers } from "vuelidate/lib/validators";

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
            rooms: [
                {
                    "id": 1,
                    "name": "Agent Pera",
                    "messages": [
                        {
                            "id": 1,
                            "senderID": 1,
                            "receiverID": 2,
                            "content": "Hey, man",
                        },
                        {
                            "id": 2,
                            "senderID": 2,
                            "receiverID": 1,
                            "content": "How are you?"
                        }
                    ]
                },
                {
                    "id": 2,
                    "name": "Agent Mika",
                    "messages": []
                }
            ]
        }
    },
    computed: mapGetters(["isLogged", "getUser"]),
    methods: {
        setRoom(room) {
            this.selectedRoom = room;
            this.selectedTitle = "Conversation with " + this.selectedRoom.name;
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
                    "senderID": this.getUser.id,
                    "receiverID": this.selectedRoom.id,
                    "content": this.currentMessage,
                    "username": this.getUser.firstName + " " + this.getUser.lastName 
                };
                console.log(msg);

                // send message to server

                this.currentMessages.push(msg);
                this.currentMessage = "";
            }
        }
    },
    validations: {
		currentMessage: {
			sqli
		}
	}
}
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
        padding: 1.5%;
        border: 2.5px solid #1C6EA4;
        display: inline-block;
        margin-bottom: 2.5%;
        margin-left: 55%;
        word-break: break-word;
        border-radius: 15px;
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