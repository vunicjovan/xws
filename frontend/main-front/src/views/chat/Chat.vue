<template>
    <chat-window height="680px" :currentUserId="currentUserId" :rooms="rooms" :messages="messages" theme="dark" />
</template>

<script>
import ChatWindow from 'vue-advanced-chat'
import 'vue-advanced-chat/dist/vue-advanced-chat.css'

export default {
    name: "Chat",
    components: {
      ChatWindow
    },
    data() {
        return {
            rooms: [
                {
                    roomId: 1,
                    roomName: 'Room 1',
                    lastMessage: {
                        content: 'Some random message',
                        sender_id: 1234,
                        username: 'John Doe',
                        timestamp: '10:20',
                        date: 123242424,
                        seen: false,
                        new: true
                    },
                    users: [
                        {
                            _id: 1234,
                            username: 'John Doe',
                            status: {
                            state: 'online',
                            last_changed: 'today, 14:30'
                            }
                        },
                        {
                            _id: 4321,
                            username: 'John Snow',
                            status: {
                            state: 'offline',
                            last_changed: '14 July, 20:00'
                            }
                        }
                    ],
                    typingUsers: [ 4321 ]
                }
            ],
            messages: [
                {
                    _id: 7890,
                    content: 'This is crazy!',
                    sender_id: 1234,
                    username: 'John Doe',
                    date: '13 November',
                    timestamp: '10:20',
                    seen: true,
                    disable_actions: false,
                    disable_reactions: false,
                    reactions: {
                        laughing: [
                            1234
                        ]
                    }
                }
            ],
            currentUserId: 1234,
        }
    }
}
</script>

<style>
    
</style>