<template>
    <div class="md-layout md-gutter md-alignment-center">
        <md-card v-for="ad in ads" v-bind:key="ad.id" class="md-layout-item md-xlarge-size-20 md-large-size-25 md-medium-size-40 md-small-size-50 md-xsmall-size-90">
            <md-card-header>
                <md-card-header-text>
                    <div>
                        <p class="md-title">{{ ad.brand }} {{ ad.model }}</p>
                        <p class="md-subtitle">{{ ad.price }} €</p>
                        <p class="md-subhead">{{ ad.location }}</p>
                    </div>
                </md-card-header-text>

                <md-card-media class="md-layout-item md-size-55">
                    <img :src="getPhotoURL(ad.id, ad.photos[0])" alt="Vehicle image">
                </md-card-media>
            </md-card-header>

            
            <md-card-actions>
                <md-button @click="showPhotos = true">Photos</md-button>
                <md-button @click="showModal = true">Details</md-button>
            </md-card-actions>

            <md-dialog :md-active.sync="showModal">
                <md-dialog-title>{{ ad.brand }} {{ ad.model }} ({{ ad.vehicleClass }})</md-dialog-title>
                <md-tabs md-dynamic-height>
                    <md-tab md-label="General">
                        <md-list class="md-triple-line md-dense">
                            <md-list-item>
                                <div class="md-list-item-text">
                                    <span>
                                        <b>Price:</b> <i>{{ ad.price }} </i>
                                        <i class="fas fa-euro-sign"></i>
                                        <br><br>
                                        <i class="fas fa-search-location"></i>
                                        <b> Location:</b> <i>{{ ad.location }}</i>
                                        <br><br>
                                        <i class="fas fa-car"></i>
                                        <b> Traveled:</b> <i>{{ ad.kmTraveled }} km</i>
                                        <br><br>
                                        <i class="fas fa-ban"></i>
                                        <b> Daily travel limit:</b> <i v-if="ad.dailyLimit !== -1">{{ ad.dailyLimit }} km</i><i v-else>None</i>
                                        <br><br>
                                        <i class="fas fa-baby"></i>
                                        <b> Seats for children:</b> <i>{{ ad.childSeatNumber }}x</i>
                                    </span>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-tab>
                    <md-tab md-label="Technical details">
                        <md-list class="md-triple-line md-dense">
                            <md-list-item>
                                <div class="md-list-item-text">
                                    <span>
                                        <i class="fas fa-gas-pump"></i>
                                        <b> Fuel type:</b> <i>{{ ad.fuel }} </i>
                                        <br><br>
                                        <i class="fas fa-wrench"></i>
                                        <b> Gearbox type:</b> <i>{{ ad.gearbox }}</i>
                                        <br><br>
                                        <i class="fas fa-car-crash"></i>
                                        <b> Collision damage waiver:</b> <i v-if="ad.cdw">Supported</i><i v-else>Not supported</i>
                                        <br><br>
                                        <i class="fab fa-android"></i>
                                        <b> Android:</b> <i v-if="ad.android">Supported</i><i v-else>Not supported</i>
                                        <br><br>
                                        <b>Description:</b><br><i>{{ ad.description }}</i>
                                    </span>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-tab>
                </md-tabs>
            </md-dialog>
            <md-dialog :md-active.sync="showPhotos">
                <div class="somediv">
                    <swiper class="swiper" :options="swiperOption">
                        <swiper-slide v-for="photo in ad.photos" :key="photo">
                            <img class="images" :src="getPhotoURL(ad.id, photo)" alt="Vehicle image" />
                        </swiper-slide>
                        <div class="swiper-button-prev" slot="button-prev"></div>
                        <div class="swiper-button-next" slot="button-next"></div>
                    </swiper>
                </div>
            </md-dialog>
        </md-card>
    </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'

export default {
    name: "AdvertisementList",
    components: {
        Swiper,
        SwiperSlide
    },
    data: function() {
        return {
            swiperOption: {
                slidesPerView: 1,
                spaceBetween: 30,
                loop: true,
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true
                },
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev'
                }
            },
            showModal: false,
            showPhotos: false,
            ads: [
                {
                    "id": 1,
                    "brand": "Mercedes",
                    "model": "MP40",
                    "vehicleClass": "Sportscar",
                    "price": 1000,
                    "location": "Dortmund",
                    "fuel": "Diesel",
                    "gearbox": "Dual clutch",
                    "kmTraveled": 1345,
                    "dailyLimit": 46,
                    "childSeatNumber": 2,
                    "android": true,
                    "cdw": false,
                    "description": "That's a hell of a car right there, sir.",
                    "photos": [
                        "https://www.mercedes-benz.com/en/company/_jcr_content/root/slider/sliderchilditems/slideritem/image/MQ7-0-image-20191025121730/01-mercedes-benz-ag-company-3400x1440.jpeg",
                        "https://www.mercedes-benz.com/en/eq/_jcr_content/root/slider_copy/sliderchilditems/slideritem_1624211537/image/MQ7-0-image-20190910102600/01-mercedes-benz-vision-eqs-show-car-mercedes-benz-eq-3400x1440.jpeg",
                        "https://robbreportedit.files.wordpress.com/2019/09/19c0690_025.jpg",
                        "https://img-ik.cars.co.za/images/2019/9/Merc%20EQS/tr:n-news_large/mercedes-benz-vision-eqs-2019%203.jpg"
                    ]
                },
                {
                    "id": 2,
                    "brand": "Mercedes",
                    "model": "MP40",
                    "vehicleClass": "Sportscar",
                    "price": 1000,
                    "location": "Dortmund",
                    "fuel": "Diesel",
                    "gearbox": "Dual clutch",
                    "kmTraveled": 1345,
                    "dailyLimit": 46,
                    "childSeatNumber": 2,
                    "android": true,
                    "cdw": false,
                    "description": "That's a hell of a car right there, sir.",
                    "photos": [
                        "https://www.mercedes-benz.com/en/company/_jcr_content/root/slider/sliderchilditems/slideritem/image/MQ7-0-image-20191025121730/01-mercedes-benz-ag-company-3400x1440.jpeg",
                        "https://www.mercedes-benz.com/en/eq/_jcr_content/root/slider_copy/sliderchilditems/slideritem_1624211537/image/MQ7-0-image-20190910102600/01-mercedes-benz-vision-eqs-show-car-mercedes-benz-eq-3400x1440.jpeg",
                        "https://robbreportedit.files.wordpress.com/2019/09/19c0690_025.jpg",
                        "https://img-ik.cars.co.za/images/2019/9/Merc%20EQS/tr:n-news_large/mercedes-benz-vision-eqs-2019%203.jpg"
                    ]
                },
                {
                    "id": 3,
                    "brand": "Mercedes",
                    "model": "MP40",
                    "vehicleClass": "Sportscar",
                    "price": 1000,
                    "location": "Dortmund",
                    "fuel": "Diesel",
                    "gearbox": "Dual clutch",
                    "kmTraveled": 1345,
                    "dailyLimit": 46,
                    "childSeatNumber": 2,
                    "android": true,
                    "cdw": false,
                    "description": "That's a hell of a car right there, sir.",
                    "photos": [
                        "https://www.mercedes-benz.com/en/company/_jcr_content/root/slider/sliderchilditems/slideritem/image/MQ7-0-image-20191025121730/01-mercedes-benz-ag-company-3400x1440.jpeg",
                        "https://www.mercedes-benz.com/en/eq/_jcr_content/root/slider_copy/sliderchilditems/slideritem_1624211537/image/MQ7-0-image-20190910102600/01-mercedes-benz-vision-eqs-show-car-mercedes-benz-eq-3400x1440.jpeg",
                        "https://robbreportedit.files.wordpress.com/2019/09/19c0690_025.jpg",
                        "https://img-ik.cars.co.za/images/2019/9/Merc%20EQS/tr:n-news_large/mercedes-benz-vision-eqs-2019%203.jpg"
                    ]
                },
                {
                    "id": 4,
                    "brand": "Mercedes",
                    "model": "MP40",
                    "vehicleClass": "Sportscar",
                    "price": 1000,
                    "location": "Dortmund",
                    "fuel": "Diesel",
                    "gearbox": "Dual clutch",
                    "kmTraveled": 1345,
                    "dailyLimit": 46,
                    "childSeatNumber": 2,
                    "android": true,
                    "cdw": false,
                    "description": "That's a hell of a car right there, sir.",
                    "photos": [
                        "https://www.mercedes-benz.com/en/company/_jcr_content/root/slider/sliderchilditems/slideritem/image/MQ7-0-image-20191025121730/01-mercedes-benz-ag-company-3400x1440.jpeg",
                        "https://www.mercedes-benz.com/en/eq/_jcr_content/root/slider_copy/sliderchilditems/slideritem_1624211537/image/MQ7-0-image-20190910102600/01-mercedes-benz-vision-eqs-show-car-mercedes-benz-eq-3400x1440.jpeg",
                        "https://robbreportedit.files.wordpress.com/2019/09/19c0690_025.jpg",
                        "https://img-ik.cars.co.za/images/2019/9/Merc%20EQS/tr:n-news_large/mercedes-benz-vision-eqs-2019%203.jpg"
                    ]
                }
            ]
        };
    },
    mounted: function() {
        this.$store.dispatch("getAllAdvertisements");
        this.show = !this.show;
    },
    computed: {
        ...mapGetters(["getAdvertisements", "getUser", "isLogged"]),
    },
    methods: {
        ...mapActions(["getAllAdvertisements"]),
        getPhotoURL(advertisementId, photoName) {
            return photoName;
            //return `http://localhost:8089/agent/images/${advertisementId}/${photoName}/`;
        },

        addCartItem(advertisementId) {
            let payload = {
                cartId: this.getUser.id,
                cartItemId: advertisementId
            };
            
            this.$store.dispatch("addCartItem", payload);
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

    .md-list-item {
        margin: 0 auto;
    }

    .swiper {
        width: 100%;
        padding: 2.5%;
    }

    .images {
        width: 100%;
        display: block;
        margin-left: auto;
        margin-right: auto;
    }
</style>