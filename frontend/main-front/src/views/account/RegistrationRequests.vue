<template>
    <div v-if="isLogged && getUser !== null && getUser.roles.includes('ADMIN')" class="somediv">
        <div class="md-headline">Agents to be registered</div>
        <md-list class="md-triple-line md-dense" v-for="req in getRegistrationRequests" v-bind:key="req.id">
            <md-list-item>
                <md-avatar>
                    <i class="fa fa-user-circle fa-2x" aria-hidden="true"></i>
                </md-avatar>

                <div class="md-list-item-text">
                    <span class="ownerc">{{ req.firstName }} {{ req.lastName }}</span>
                </div>
                
                <span class="some-span">
                    <md-button @click="acceptRequest(req.id)" class="md-icon-button md-list-action">
                        <i class="fas fa-check-circle fa-2x"></i>
                        <md-tooltip>Register agent</md-tooltip>
                    </md-button>
                    <md-button @click="rejectRequest(req.id)" class="md-icon-button md-list-action">
                        <i class="fas fa-times-circle fa-2x"></i>
                        <md-tooltip>Reject request</md-tooltip>
                    </md-button>
                </span>
            </md-list-item>
            <md-divider class="md-inset"></md-divider>
        </md-list>
    </div>
</template>

<script>
import { mapGetters, mapAction, mapActions } from "vuex";
export default {
    name: "RegistrationRequests",
    computed: {
		...mapGetters(["getRegistrationRequests", "isLogged", "getUser"]),
		registrationRequests: {
			get() {
				return this.getRegistrationRequests;
			},
			set(registrationRequests) {
				this.$store.commit("setRegistrationRequests", registrationRequests);
			},
		},
    },
    mounted: function() {
        this.$store.dispatch("getRegistrationRequests");
    },
    methods: {
        ...mapActions(["getRegistrationRequests", "sendRegistrationResponse"]),

        acceptRequest(reqId) {
            var resp = {
                "id": reqId,
                "accepted": true
            }
            this.$store.dispatch("sendRegistrationResponse", resp);
        },

        rejectRequest(reqId) {
            var resp = {
                "id": reqId,
                "accepted": false
            }
            this.$store.dispatch("sendRegistrationResponse", resp);
        }
    }
}
</script>

<style>
    p {
        word-break: break-all;
        white-space: normal;
        padding-right: 30%;
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    }
    .ownerc {
        font-family: 'Courier New', Courier, monospace;
    }
    .contentc {
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    }
    .some-span {
        padding-right: 2.5%;
    }
    .somediv {
        width: 70%;
        margin: 0 auto;
    }
    .md-headline {
        width: 50%;
        margin: 2.5% auto;
        font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
        text-align: center;
        vertical-align: middle;
    }
</style>
