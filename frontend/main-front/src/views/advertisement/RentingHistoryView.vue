<template>
    <transition>
        <div>
            <div class="md-headline">Renting History</div>
            <div v-if="show && isLogged" class="md-layout md-gutter md-alignment-center">
                <div class="card-expansion">
                    <md-card v-for="ad in getRentingHistory" v-bind:key="ad.advertisement.id" class="md-layout-item md-size-90">
                        
                        <div id="left-div">
                            <md-card-header>
                                <md-card-header-text>
                                    <p class="md-title">{{ ad.advertisement.brand }} {{ ad.advertisement.model }}</p>
                                    <p class="md-subtitle">{{ ad.advertisement.price }}€</p>
                                    <p class="md-subhead">{{ ad.advertisement.location }}</p>
                                </md-card-header-text>
                            </md-card-header>

                            <md-button v-if="ad.commentAvailable" @click="showCommentDialog = true">Comment</md-button>
                            <md-button @click="showRateDialog = true">Rate</md-button>
                            
                            <md-dialog @md-opened="openCommentDialog()" :md-active.sync="showCommentDialog" md-dynamic-height md-dynamic-width>
                                <md-dialog-title>Comment Advertisement</md-dialog-title>

                                <md-dialog-content>
                                    <md-field :class="{ 'md-invalid': $v.commentTitle.$error }">
                                        <label>Comment title</label>
                                        <md-textarea v-model="commentTitle" md-autogrow>
                                            <span class="md-error" v-if="!$v.commentTitle.required">Comment title is required</span>
                                        </md-textarea>
                                    </md-field>
                                    <md-field :class="{ 'md-invalid': $v.commentContent.$error }">
                                        <label>Comment content</label>
                                        <md-textarea v-model="commentContent" md-autogrow>
                                            <span class="md-error" v-if="!$v.commentContent.required">Comment text is required</span>
                                        </md-textarea>
                                    </md-field>
                                </md-dialog-content>
                                <md-dialog-actions>
                                        <md-button @click="validateComment(ad)" class="md-primary">Post</md-button>
                                        <md-button @click="showCommentDialog = false" class="md-primary">Cancel</md-button>
                                </md-dialog-actions>

                            </md-dialog>

                            <md-dialog @md-opened="openRateDialog()" :md-active.sync="showRateDialog" md-dynamic-height md-dynamic-width>
                                <md-dialog-title>Rate Advertisement</md-dialog-title>
                                
                                <md-dialog-content>
                                    <md-field :class="{ 'md-invalid': $v.rating.$error }">
                                        <label>Advertisement rating</label>
                                        <md-input v-model="rating" type="number" min="1" max="5">
                                            <span class="md-error" v-if="!$v.rating.required">Rating is required</span>
                                        </md-input>
                                    </md-field>
                                </md-dialog-content>

                                <md-dialog-actions>
                                    <md-button @click="validateRating(ad)" class="md-primary">Post</md-button>
                                    <md-button @click="showRateDialog = false" class="md-primary">Cancel</md-button>
                                </md-dialog-actions>
                            </md-dialog>
                        </div>
                        
                        <div right-div>
                            <md-card-content>
                                <md-tabs class="md-transparent" md-alignment="fixed">
                                    <md-tab md-label="Intervals">
                                        <md-table>
                                            <md-table-row v-for="interval in ad.rentingIntervals" v-bind:key="interval.id">
                                                <md-table-cell md-label="Start Date">     
                                                    {{ new Date(interval.startDate).getDate() }}-{{ new Date(interval.startDate).getMonth() + 1 }}-{{
													new Date(interval.startDate).getFullYear()
												}}
												{{ new Date(interval.startDate).getHours() }}:{{ new Date(interval.startDate).getMinutes() }} 
                                                </md-table-cell>
                                                <md-table-cell md-label="End Date">     
                                                        {{ new Date(interval.endDate).getDate() }}-{{ new Date(interval.endDate).getMonth() + 1 }}-{{ new Date(interval.endDate).getFullYear() }}
												{{ new Date(interval.endDate).getHours() }}:{{ new Date(interval.endDate).getMinutes() }} 
                                                </md-table-cell>
                                            </md-table-row>
                                        </md-table>
                                    </md-tab>
                                    <md-tab md-label="My Comments">
                                        <md-table>
                                            <md-table-row v-for="comment in ad.comments" v-bind:key="comment.id">
                                                <md-table-cell md-label="Title">
                                                        {{ comment.title }}
                                                </md-table-cell>
                                                <md-table-cell md-label="Content">
                                                        {{ comment.content }}
                                                </md-table-cell>
                                            </md-table-row>
                                        </md-table>
                                    </md-tab>
                                </md-tabs>
                            </md-card-content>
                        </div>
                    </md-card>
                </div>
            </div>
        </div>
    </transition>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
export default {
    mixins: [validationMixin],

    name: "RentingHistoryView",
    
    data: function() {
        return {
            show: false,
            showCommentDialog: false,
            showRateDialog: false,
            commentContent: "",
            commentTitle: "",
            rating: undefined,
        };
    },

    mounted: function() {
        this.show = !this.show;
        if (this.getUser) {
            this.$store.dispatch("getUserRentingHistory", this.getUser.id);
        }
    },

    computed: {
        ...mapGetters(["getUser", "isLogged", "getRentingHistory"]),
        ...mapGetters(["getUser", "isLogged"]),
    },

    methods: {
        ...mapActions(["getUserRentingHistory", "postComment"]),
        openCommentDialog: function() {
            this.$v.$reset();
            this.commentContent = "";
            this.commentTitle = "";
        },
        openRateDialog: function() {
            this.$v.$reset();
            this.rating = undefined;
        },
        commentOnAd: function(ad) {
            let comment = {
                "title": this.commentTitle,
                "content": this.commentContent,
                "userId": this.getUser.id,
                "advertisementId": ad.advertisement.id,
                "rentingRequestId": ad.rentingRequestId
            }

            this.$store
                .dispatch("postComment", comment)
                .then((data) => {
                    this.showCommentDialog = false;
                })
                .catch((error) => console.log(error));
        },
        rateAd: function(ad) {
            let rate ={
                "adId": ad.advertisement.id,
                "rating": {    
                    "rating": this.rating,
                    "userId": this.getUser.id
                }
            }
            this.$store
                .dispatch("postRating", rate)
                .then((data) => {
                    this.showRateDialog = false;
                }) 
                .catch((error) => console.log(error));
        },
        validateComment: function(ad) {
            this.$v.$touch();

            if (!this.$v.$invalid) {
                this.showCommentDialog = false;
                this.commentOnAd(ad);
            }
        },
        validateRating: function(ad) {
            this.$v.$touch();

            if (!this.$v.$$invalid) {
                this.showRateDialog = false;
                this.rateAd(ad);
            }
        }
    },

    watch: {
        getUser: function(val) {
            if (val) {
                this.$store.dispatch("getUserRentingHistory", val.id);
            }
        }
    },

    validations: {
        commentContent: {
            required,
        },
        commentTitle: {
            required,
        },
        rating: {
            required,
        }
    }
}
</script>

<style>
    .md-dialog /deep/ .md-dialog-container {
	/* width: 100%; */
    /* height: 110%; */
    width: 40%;
	max-height: 95%;
	position: absolute;
	/* left: 50%;
	top: 50%;
	transform: translate(-50%, -50%); */
    }

    .md-card {
        margin: 2.5%;
        display: inline-block;
        vertical-align: top;
        max-width: 90%;
    }

    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 1s;
    }

    .fade-enter,
    .fate-leave-to {
        opacity: 0;
    }

    .card-expansion {
        margin: auto;
        width: 50%;
    }

    #left-div {
	width: 40%;
	float: left;
    }

    #right-div {
        width: 40%;
        float: right;
    }
    
</style>
