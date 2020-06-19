<template>
    <transition>
        <div v-if="isLogged" class="md-layout md-alignment-top-center">
            <md-card>
                <md-card-header> 
                    <md-card-header-text>               
                        <p class="md-title">Start Date</p>
                        <p class="md-subtitle">
                            {{ new Date(rentingRequest.startDate).getDate() }}-{{ new Date(rentingRequest.startDate).getMonth()+1 }}-{{ new Date(rentingRequest.startDate).getFullYear() }} {{ new Date(rentingRequest.startDate).getHours() }}:{{ new Date(rentingRequest.startDate).getMinutes() }}
                        </p>
                        <p class="md-title">End Date</p>
                        <p class="md-subtitle">
                            {{ new Date(rentingRequest.endDate).getDate() }}-{{ new Date(rentingRequest.endDate).getMonth()+1 }}-{{ new Date(rentingRequest.endDate).getFullYear() }} {{ new Date(rentingRequest.endDate).getHours() }}:{{ new Date(rentingRequest.endDate).getMinutes() }}
                        </p>
                    </md-card-header-text>
                </md-card-header>
                <md-card-content>
                    <md-table class="md-layout-item md-alignment-center md-size-100">
                        <md-table-row>
                            <md-table-head>Brand</md-table-head>
                            <md-table-head>Model</md-table-head>
                            <md-table-head>Location</md-table-head>
                            <md-table-head>Price</md-table-head>
                        </md-table-row>
                        <md-table-row v-for="ad in rentingRequest.advertisements" v-bind:key="ad.id"> 
                            <md-table-cell md-label="Brand">{{ ad.brand }}</md-table-cell>
                            <md-table-cell md-label="Model">{{ ad.model }}</md-table-cell>
                            <md-table-cell md-label="Location">{{ ad.location }}</md-table-cell>
                            <md-table-cell md-label="Price">{{ ad.price }}</md-table-cell>
                        </md-table-row>
                    </md-table>
                </md-card-content>
                <md-card-actions>
                    <md-button @click="acceptRequest(rentingRequest)" class="md-primary">Accept</md-button>
                    <md-button @click="declineRequest(rentingRequest)" class="md-accent">Decline</md-button>
                </md-card-actions>
            </md-card>
        </div>
    </transition>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
    name: "RentingRequest",

    data: function() {
        return {
            rentingRequest:  
                {
                    "id": 1,
                    "startDate": "2020-10-21T12:39:06.000+00:00",
                    "endDate": "2020-10-21T13:38:06.000+00:00",
                    "senderId": 3,
                    "advertisements": [
                        {
                            "id": 1,
                            "model": "model1",
                            "brand": "brend1",
                            "price": 123,
                            "location": "location1"
                        },
                        {
                            "id": 2,
                            "model": "model2",
                            "brand": "brend2",
                            "price": 2222,
                            "location": "location2"
                        }
                    ]
                }
            }
    },

    computed: {
        ...mapGetters(["isLogged", "getUser", "getRentingRequest"]),
        // rentingRequest: {
        //     get() {
        //         return this.getRentingRequest;
        //     },
        //     set(rentingRequest) {
        //         this.$store.commit("setRentingRequest", rentingRequest);
        //     }
        // }
    },

    mounted() {
        this.$store.dispatch("getUserRentingRequest", this.$route.params.id);
    },

    methods: {
        ...mapActions(["getUserRentingRequest", "updateRentingRequestStatus"]),
        acceptRequest: function(request) {
            let ret = {
                "id": request.id,
                "status": 2
            }
            
            this.updateRentingRequestStatus(ret);
            this.$router.push('/ads/published');
        },
        declineRequest: function(request) {
            let ret = {
                "id": request.id,
                "status": 3
            }

            this.updateRentingRequestStatus(ret);
            this.$router.push('/ads/published');
        },
    }
}
</script>

<style>
    .md-card {
        margin: 2.5%;
        display: inline-block;
        vertical-align: top;
    }

    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 1s;
    }

    .fade-enter,
    .fate-leave-to {
        opacity: 0;
    }

</style>
