<template>
    <transition>
        <div v-if="show && isLogged" class="md-layout md-gutter md-alignment-center">
            <div class="card-expansion">
                <md-card v-for="ad in ads" v-bind:key="ad.id" class="md-layout-item md-size-90">
                    <md-card-header>
                        <md-card-header-text>
                            <p class="md-title">{{ ad.brand }} {{ ad.model }}</p>
                            <p class="md-subtitle">{{ ad.price }} €</p>
                            <p class="md-subhead">{{ ad.location }}</p>
                        </md-card-header-text>
                    </md-card-header>

                    <md-card-expand>
                        <md-card-expand-trigger>
                            <md-button>Requests</md-button>
                        </md-card-expand-trigger>

                        <md-card-expand-content>
                            <md-card-content>
                                <md-table class="md-layout-item md-size-85">
                                    <md-table-row>
                                        <md-table-head>Start Date</md-table-head>
                                        <md-table-head>End Date</md-table-head>
                                        <md-table-head>Manage</md-table-head>
                                    </md-table-row>
                                    <md-table-row v-for="request in extractRequestsForAd(ad.id)" v-bind:key="request.id" slot="md-table-row">
                                        <md-table-cell md-label="Start Date">{{ new Date(request.startDate).getDate() }}-{{ new Date(request.startDate).getMonth()+1 }}-{{ new Date(request.startDate).getFullYear() }} {{ new Date(request.startDate).getHours() }}:{{ new Date(request.startDate).getMinutes() }}</md-table-cell>
                                        <md-table-cell md-label="End Date">{{ new Date(request.endDate).getDate() }}-{{ new Date(request.endDate).getMonth()+1 }}-{{ new Date(request.endDate).getFullYear() }} {{ new Date(request.endDate).getHours() }}:{{ new Date(request.endDate).getMinutes() }}</md-table-cell>
                                        <md-table-cell md-label="Manage">
                                            <md-button @click.native="$router.push('/ads/published/request/'+request.id)" class="md-icon-button">
                                                <md-icon class="fas fa-pen"/>
                                            </md-button>
                                        </md-table-cell>
                                    </md-table-row>
                                </md-table>
                            </md-card-content>
                        </md-card-expand-content>
                    </md-card-expand>

                </md-card>
            </div>
        </div>
    </transition>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
    name: "PublishedView",
    
    data: function() {
        return {
            show: false,
            ads: [
                {
                    "id": 1,
                    "brand": "brend1",
                    "model": "model1",
                    "price": "price1",
                    "location": "location1"
                },
                {
                    "id": 2,
                    "brand": "brend2",
                    "model": "model2",
                    "price": "price2",
                    "location": "location2"
                },
                {
                    "id": 3,
                    "brand": "brend3",
                    "model": "model3",
                    "price": "price3",
                    "location": "location3"
                },
            ],
            requests: [
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
                            "location": "location1"
                        },
                        {
                            "id": 2,
                            "model": "model2",
                            "brand": "brend2",
                            "location": "location2"
                        }
                    ]
                },
                {
                    "id": 2,
                    "startDate": "2020-10-22T12:39:06.000+00:00",
                    "endDate": "2020-10-22T13:38:06.000+00:00",
                    "senderId": 3,
                    "advertisements": [
                        {
                            "id": 3,
                            "model": "model3",
                            "brand": "brend3",
                            "location": "location3"
                        },
                        {
                            "id": 2,
                            "model": "model2",
                            "brand": "brend2",
                            "location": "location2"
                        }
                    ]
                }
            ]
        }
    },

    mounted: function() {
        // this.$store.dispatch("getUserPublishedAdvertisements");
        // this.$store.dispatch("getUserRentingRequests")
        this.show = !this.show;
    },

    computed: {
        ...mapGetters(["getUser", "isLogged"]),//"getAdvertisements", "getRentingRequests"
    },

    methods: {
        // ...mapActions(["getUserPublishedAdvertisements", "getUserRentingRequests"]),
        extractRequestsForAd(adId) {
            let retList = [];
            for (let i = 0; i < this.requests.length; i++) {
                for (let j = 0; j < this.requests[i].advertisements.length; j++) {
                    if (adId == this.requests[i].advertisements[j].id){
                        retList.push(this.requests[i]);
                    }
                }
            }
            return retList;
        }
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
