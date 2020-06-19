<template>
    <div class="md-layout md-gutter md-alignment-center">
        <md-card v-for="ad in getAdvertisements" v-bind:key="ad.id" class="md-layout-item md-xlarge-size-20 md-large-size-25 md-medium-size-40 md-small-size-50 md-xsmall-size-90">
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
                <md-button @click="showDialog(ad)">Details</md-button>
            </md-card-actions>

            <md-dialog v-if="selectedAdvertisement" :md-active.sync="showModal">
                <md-dialog-title>{{ selectedAdvertisement.brand }} {{ selectedAdvertisement.model }} ({{ selectedAdvertisement.vehicleClass }})</md-dialog-title>
                <md-tabs md-dynamic-height>
                    <md-tab md-label="General">
                        <md-list class="md-triple-line md-dense">
                            <md-list-item>
                                <div class="md-list-item-text">
                                    <span>
                                        <b>Price:</b> <i>{{ selectedAdvertisement.price }} </i>
                                        <i class="fas fa-euro-sign"></i>
                                        <br><br>
                                        <i class="fas fa-search-location"></i>
                                        <b> Location:</b> <i>{{ selectedAdvertisement.location }}</i>
                                        <br><br>
                                        <i class="fas fa-car"></i>
                                        <b> Traveled:</b> <i>{{ selectedAdvertisement.kmTraveled }} km</i>
                                        <br><br>
                                        <i class="fas fa-ban"></i>
                                        <b> Daily travel limit:</b> <i v-if="selectedAdvertisement.dailyLimit !== -1">{{ selectedAdvertisement.dailyLimit }} km</i><i v-else>None</i>
                                        <br><br>
                                        <i class="fas fa-baby"></i>
                                        <b> Seats for children:</b> <i>{{ selectedAdvertisement.childSeatNumber }}x</i>
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
                                        <b> Fuel type:</b> <i>{{ selectedAdvertisement.fuel }} </i>
                                        <br><br>
                                        <i class="fas fa-wrench"></i>
                                        <b> Gearbox type:</b> <i>{{ selectedAdvertisement.gearbox }}</i>
                                        <br><br>
                                        <i class="fas fa-car-crash"></i>
                                        <b> Collision damage waiver:</b> <i v-if="selectedAdvertisement.cdw">Supported</i><i v-else>Not supported</i>
                                        <br><br>
                                        <i class="fab fa-android"></i>
                                        <b> Android:</b> <i v-if="selectedAdvertisement.android">Supported</i><i v-else>Not supported</i>
                                        <br><br>
                                        <b>Description:</b><br><i>{{ selectedAdvertisement.description }}</i>
                                    </span>
                                </div>
                            </md-list-item>
                        </md-list>
                    </md-tab>
                    <md-tab md-label="Rent intervals">
                        <form class="md-layout md-alignment-top-center">
                            <md-card class="md-layout-item md-size-40 md-small-size-100">
                                <md-card-header>
                                    <div class="md-title">Unavailable intervals</div>
                                </md-card-header>
                                <md-card-content>
                                    <md-datepicker :class="{ 'md-invalid': $v.rentingInterval.startDate.$error }" v-model="rentingInterval.startDate">
                                        <label>Select start date</label>
                                        <span class="md-error" v-if="!$v.rentingInterval.startDate.required">Start date is required</span>
                                    </md-datepicker>
                                    <md-datepicker :class="{ 'md-invalid': $v.rentingInterval.endDate.$error }" v-model="rentingInterval.endDate">
                                        <label>Select end date</label>
                                        <span class="md-error" v-if="!$v.rentingInterval.endDate.required">End date is required</span>
                                    </md-datepicker>
                                </md-card-content>
                                <md-card-actions>
                                    <md-button class="md-primary" @click="resetDates()" >Reset</md-button>
                                    <md-button class="md-primary" type="submit"  @click.prevent="validateDates">Add</md-button>
                                </md-card-actions>
                            </md-card>
                        </form>
                    </md-tab>
                    <md-tab md-label="Comments">
                        <div v-if="selectedAdvertisement.comments.length != 0">
                            <md-content class="md-scrollbar comments">
                                <div v-for="comment in selectedAdvertisement.comments" :key="comment.id" class="md-layout md-alignment-top-center">
                                    <md-card class="md-layout-item md-size-40 md-small-size-100" >
                                        <md-card-header>
                                            <div class="md-title">{{ comment.title }}</div>
                                        </md-card-header>
                                        <md-card-content>
                                            <p>{{ comment.content }}</p>
                                        </md-card-content>
                                    </md-card>
                                </div>
                            </md-content>
                        </div>
                        <div v-else>
                            <md-empty-state md-label="No comments yet!"></md-empty-state>
                        </div>
                    </md-tab>
                    <md-tab md-label="Add comment">
                        <form class="md-layout md-alignment-top-center">
                            <md-card class="md-layout-item md-size-40 md-small-size-100">
                                <md-card-header>
                                    <div class="md-title">Post comment</div>
                                </md-card-header>
                                <md-card-content>
                                    <div class="md-layout">
                                    <div class="md-layout-item md-small-size-100">
                                        <md-field :class="getValidationClass('title')">
                                            <label>Title</label>
                                            <md-input type="text" v-model="comment.title"></md-input>
                                            <span class="md-error" v-if="!$v.comment.title.required">Title is rquired</span>
                                            <span class="md-error" v-if="!$v.comment.title.lrx">Title is not well formed</span>
                                        </md-field>
                                    </div>
                                    </div>
                                    <div class="md-layout">
                                    <div class="md-layout-item md-small-size-100">
                                        <md-field :class="getValidationClass('content')">
                                            <label>Content</label>
                                            <md-textarea type="text" v-model="comment.content" />
                                            <span class="md-error" v-if="!$v.comment.content.required">Content is required</span>
                                            <span class="md-error" v-if="!$v.comment.content.lrx">Content is not well formed</span>
                                        </md-field>
                                    </div>
                                    </div>
                                </md-card-content>
                                <md-card-actions>
                                    <md-button class="md-primary" @click="resetComment()">Reset</md-button>
                                    <md-button class="md-primary" type="submit" @click.prevent="validateComment">Add</md-button>
                                </md-card-actions>
                            </md-card>
                        </form>
                    </md-tab>
                </md-tabs>
            </md-dialog>
            <md-dialog v-if="ad.photos.length != 0" :md-active.sync="showPhotos">
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
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
const lrx = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(([A-ZČĆŽŠĐ]){1,}[a-zčćšđžA-ZČĆŽŠĐ]+\s?)+$/);

export default {
    name: "AdvertisementList",
    mixins: [validationMixin],
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
            ],
            selectedAdvertisement: undefined,
            rentingInterval: {
                startDate: undefined,
                endDate: undefined,
                advertisementId: undefined
            },

            comment: {
                title: undefined,
                content: undefined,
                advertisementId: undefined,
            }
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
        },

        addUnabailableTerm() {
            this.rentingInterval.advertisementId = this.selectedAdvertisement.id
			this.$store.dispatch("addRentingInterval", this.rentingInterval);
		},

		resetDates() {
			this.$v.rentingInterval.$reset();
			this.rentingInterval.startDate = undefined;
			this.rentingInterval.endDate = undefined;
        },
        
        validateDates() {
			this.$v.rentingInterval.$touch();

			if (!this.$v.rentingInterval.$invalid) {
				this.addUnabailableTerm();
			}
        },

        postComment() {
            this.comment.advertisementId = this.selectedAdvertisement.id;
            this.$store.dispatch("postComment", this.comment);
        },

        resetComment() {
            this.$v.comment.$reset();
            this.comment.title = undefined;
            this.comment.content = undefined;
        },

        validateComment() {
            this.$v.comment.$touch();

			if (!this.$v.comment.$invalid) {
				this.postComment();
			}
        },

        getValidationClass(fieldName) {
			const field = this.$v.comment[fieldName];

			if (field) {
				return {
					"md-invalid": field.$invalid && field.$dirty,
				};
			}
		},
        
        showDialog(selectedAd) {
            this.selectedAdvertisement = selectedAd;
            this.showModal = true;
        }
    },

    validations: {
		rentingInterval: {
			startDate: {
				required,
			},
			endDate: {
				required,
			},
        },
        
        comment: {
            title: {
                required,
                lrx,
            },
            content: {
                required,
                lrx,
            }
        }
	},
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

    .comments {
    max-width: 200px;
    max-height: 400px;
    overflow: auto;
  }
</style>
