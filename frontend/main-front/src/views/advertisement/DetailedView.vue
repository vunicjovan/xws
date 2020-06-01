<template>
    <transition name="fade">
        <div v-if="show" class="md-layout md-alignment-center-center">
            <md-card>
                <md-card-media>
                    <hooper :centerMode="true" :itemsToShow="1" :infiniteScroll="true" :progress="true" :autoPlay="true" :playSpeed="2000">
                        <slide v-for="photo in this.ad.photos" :key="photo">
                            <img class="images img1" :src="photo" alt="Vehicle image">
                        </slide>
                        <hooper-pagination slot="hooper-addons"></hooper-pagination>
                    </hooper>
                </md-card-media>

                <md-card-header>
                    <div class="md-title"><b>{{ this.ad.brand }} {{ this.ad.model }}</b> ({{ this.ad.class }}) - Owned by <b>{{ this.ad.owner }}</b></div>
                    <div class="md-subhead"><b>Location:</b> {{ this.ad.place }}</div>
                    <div class="md-subhead"><b>Price:</b> {{ this.ad.price }} EU</div>
                    <div class="md-subhead"><b>Fuel:</b> {{ this.ad.fuel }}</div>
                    <div class="md-subhead"><b>Gearbox:</b> {{ this.ad.gearbox }}</div>
                </md-card-header>

                <md-card-content>
                    <div>
                        <b>Advanced details:</b><br>
                        <i>This vehicle has traveled {{ this.ad.km_traveled }} kilometers.
                        It has a daily kilometer limit of {{ this.ad.daily_limit }} kilometers
                        for crossing. Number of children seats in this vehicle is 
                        {{ this.ad.childseat_number }}</i>.
                        <i v-if="this.ad.android">It has android support.</i>
                        <i v-else-if="!this.ad.android">It doesn't have android support.</i>
                        <i v-if="this.ad.cdw"> This vehicle has Collision Damage Waiver policy.</i>
                        <i v-else-if="!this.ad.cdw">This vehicle doesn't have Collision Damage Waiver 
                        policy.</i>
                    </div>
                    <br>
                    <div><b>Owner's description:</b><br>{{ this.ad.description}}</div>
                </md-card-content>

                <md-card-actions>
                    <md-button class="md-raised md-accent nana">Add to cart</md-button>
                </md-card-actions>
            </md-card>
        </div>
    </transition>
</template>

<script>
import { Hooper, Slide, Pagination as HooperPagination } from 'hooper';
import 'hooper/dist/hooper.css';

export default {
    name: "DetailedView",
    components: {
        Hooper,
        Slide,
        HooperPagination
    },
    data: function() {
        return {
            ad: {
                "id": 1,
                "brand": "BMW",
                "model": "X8",
                "class": "Sports Car",
                "gearbox": "Dual Clutch",
                "fuel": "Bio Diesel",
                "owner": "John Doe",
                "place": "Neverland",
                "price": 1500,
                "km_traveled": 2355.56,
                "cdw": false,
                "daily_limit": 35.5,
                "childseat_number": 2,
                "android": true,
                "description": "This is an awesome car, you gotta love it. Maybe you think you deserve better, but does better deserve you?",
                "photos": [
                    "https://images.unsplash.com/photo-1542362567-b07e54358753?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80",
                    "https://images.pexels.com/photos/241316/pexels-photo-241316.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                    "https://media.zigcdn.com/media/model/2019/Sep/maruti-swift_360x240.jpg"
                ]
            },
            show: false
        };
    },
    mounted: function() {
        this.show = !this.show;
    }
}
</script>

<style>
    .nana {
        align-items: center;
		justify-content: center;
		padding: 5%;
    }

    .md-card {
        width: 70%;
        margin: 5%;
        padding: 1%;
        display: inline-block;
        vertical-align: top;
    }

    .images, .hooper {
        width: 60%;
        height: 10%;
        display: block;
        margin-left: auto;
        margin-right: auto;
    }

    .img1 {
        border-radius: 15%;
    }

    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 2.5s;
    }

    .fade-enter,
    .fade-leave-to {
        opacity: 0;
    }
</style>
