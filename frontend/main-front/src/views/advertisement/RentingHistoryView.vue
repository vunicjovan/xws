<template>
	<div>
		<div class="md-headline">Renting History</div>
		<flash-message class="myFlash"></flash-message>
		<div v-if="isLogged && getUser !== null && getUser.roles.includes('SIMPLE_USER')" class="md-layout md-gutter md-alignment-center">
			<div class="card-expansion">
				<md-card v-for="(ad, index) in getRentingHistory" v-bind:key="index" class="md-layout-item md-size-90">
					<div id="left-div">
						<md-card-header>
							<md-card-header-text>
								<p class="md-title">{{ ad.advertisement.brand }} {{ ad.advertisement.model }}</p>
								<p class="md-subtitle">{{ ad.advertisement.price }}€</p>
								<p class="md-subhead">{{ ad.advertisement.location }}</p>
							</md-card-header-text>
						</md-card-header>

						<md-button v-if="ad.commentAvailable" @click="openCommentDialog(ad)">Comment</md-button>
						<md-button v-if="ad.ratingAvailable" @click="openRateDialog(ad)">Rate</md-button>
						<md-dialog :md-active.sync="showCommentDialog" md-dynamic-height md-dynamic-width>
							<md-dialog-title>Comment Advertisement</md-dialog-title>

							<md-dialog-content>
								<md-field :class="{ 'md-invalid': $v.commentTitle.$error }">
									<label>Comment title</label>
									<md-textarea v-model="commentTitle" md-autogrow>
										<span class="md-error" v-if="!$v.commentTitle.required">Comment title is required</span>
										<span class="md-error" v-else-if="!$v.commentTitle.sqli">Title is not in proper format</span>
									</md-textarea>
								</md-field>
								<md-field :class="{ 'md-invalid': $v.commentContent.$error }">
									<label>Comment content</label>
									<md-textarea v-model="commentContent" md-autogrow>
										<span class="md-error" v-if="!$v.commentContent.required">Comment text is required</span>
										<span class="md-error" v-else-if="!$v.commentContent.sqli">Comment is not in proper format</span>
									</md-textarea>
								</md-field>
							</md-dialog-content>
							
							<md-dialog-actions>
								<md-button @click="validateComment()" class="md-primary">Post</md-button>
								<md-button @click="showCommentDialog = false" class="md-primary">Cancel</md-button>
							</md-dialog-actions>
						</md-dialog>
						
                        <md-dialog :md-active.sync="showRateDialog" md-dynamic-height md-dynamic-width>
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
                                <md-button @click="validateRating()" class="md-primary">Post</md-button>
                                <md-button @click="showRateDialog = false" class="md-primary">Cancel</md-button>
                            </md-dialog-actions>
                        </md-dialog>
                    </div>
                        
                   
					<div right-div>
						<md-card-content>
							<md-tabs class="md-transparent" md-alignment="fixed">
								<md-tab md-label="Intervals">
									<md-table>
										<md-table-row>
											<md-table-cell md-label="Start Date">
												{{ new Date(ad.rentingInterval.startDate).getDate() }}-{{ new Date(ad.rentingInterval.startDate).getMonth() + 1 }}-{{
													new Date(ad.rentingInterval.startDate).getFullYear()
												}}
												{{ new Date(ad.rentingInterval.startDate).getHours() }}:{{ new Date(ad.rentingInterval.startDate).getMinutes() }}
											</md-table-cell>
											<md-table-cell md-label="End Date">
												{{ new Date(ad.rentingInterval.endDate).getDate() }}-{{ new Date(ad.rentingInterval.endDate).getMonth() + 1 }}-{{
													new Date(ad.rentingInterval.endDate).getFullYear()
												}}
												{{ new Date(ad.rentingInterval.endDate).getHours() }}:{{ new Date(ad.rentingInterval.endDate).getMinutes() }}
											</md-table-cell>
										</md-table-row>
									</md-table>
								</md-tab>
								<md-tab md-label="My Comments" v-if="ad.comment">
									<md-table>
										<md-table-row>
											<md-table-cell md-label="Title">
												{{ ad.comment.title }}
											</md-table-cell>
											<md-table-cell md-label="Content">
												{{ ad.comment.content }}
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
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { validationMixin } from "vuelidate";
import { required, helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Script|Select|From|Where)([a-zA-Z0-9!?#.,:;\s?]+)$/);

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
			sendingAd: undefined
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
		checkIfRated: function() {
			const rated = [];
			let pushed = false;
			for (let i = 0; i < this.getRentingHistory.length; i++) {
				for (let j = 0; j < this.getRentingHistory[i].advertisement.ratedByUsers.length; j++) {
					if (this.getUser.id === this.getRentingHistory[i].advertisement.ratedByUsers[j].userId && !pushed) {
						rated.push(false);
						pushed = true;
					}
				}
				if (!pushed) {
					rated.push(true);
				}
				pushed = false;
			}

			return rated;
		}
	},

    methods: {
        ...mapActions(["getUserRentingHistory", "postComment"]),
        openCommentDialog: function(ad) {
            this.$v.$reset();
            this.commentContent = "";
			this.commentTitle = "";
			this.showCommentDialog = true;
			this.sendingAd = ad;
        },
        openRateDialog: function(ad) {
            this.$v.$reset();
			this.rating = undefined;
			this.sendingAd = ad;
			this.showRateDialog = true;
        },
        commentOnAd: function() {
			let ad = this.sendingAd;
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
					this.$store.dispatch("getUserRentingHistory", this.getUser.id);
                })
                .catch((error) => this.flashWarning(error.message, {timeout: 2000}));
        },
        rateAd: function() {
			let ad = this.sendingAd;
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
					this.$store.dispatch("getUserRentingHistory", this.getUser.id);
                }) 
                .catch((error) => this.flashWarning(error.message, {timeout: 2000}));
        },
        validateComment: function() {
            this.$v.$touch();

            if (!this.$v.commentTitle.$invalid && !this.$v.commentContent.$invalid) {
                this.showCommentDialog = false;
                this.commentOnAd();
            }
        },
        validateRating: function() {
            this.$v.$touch();

            if (!this.$v.rating.$invalid) {
                this.showRateDialog = false;
                this.rateAd();
            }
		},
    },

	watch: {
		getUser: function(val) {
			if (val) {
				this.$store.dispatch("getUserRentingHistory", val.id);
			}
		},
	},

	validations: {
        commentContent: {
			required,
			sqli,
        },
        commentTitle: {
			required,
			sqli,
        },
        rating: {
            required,
        }
    }
};

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
