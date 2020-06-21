<template>
	<div>
		<div class="md-headline">Renting History</div>
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

						<md-button v-if="ad.commentAvailable" @click="showCommentDialog = true">Comment</md-button>
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
import { required } from "vuelidate/lib/validators";
import { helpers } from "vuelidate/lib/validators";
export default {
	mixins: [validationMixin],

	name: "RentingHistoryView",

	data: function() {
		return {
			show: false,
			showCommentDialog: false,
			commentContent: "",
			commentTitle: "",
			// rentHistory:
			// [
			//     {

			//         rentingRequestId: 1,
			//         advertisement:
			//         {
			//              "id": 1,
			//             "brand": "brend1",
			//             "model": "model1",
			//             "price": "price1",
			//             "location": "location1",
			//         }
			//         ,
			//         commentsAvailable: true,
			//         rentingIntervals: [
			//             {
			//                 "startDate": "2020-10-21T12:39:06.000+00:00",
			//                 "endDate": "2020-10-21T13:38:06.000+00:00"
			//             },
			//             {
			//                 "startDate": "2020-01-21T13:39:06.000+00:00",
			//                 "endDate": "2020-01-23T14:38:06.000+00:00"
			//             }
			//         ],
			//         comments: [
			//             {
			//                 "id": 1,
			//                 "text": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
			//             }
			//         ]
			//     },
			//     {
			//         rentingRequestId: null,
			//         advertisement:
			//         {
			//             "id": 2,
			//             "brand": "brend2",
			//             "model": "model2",
			//             "price": "price2",
			//             "location": "location2",
			//         }
			//         ,
			//         commentsAvailable: false,
			//         rentingIntervals: [
			//             {
			//                 "startDate": "2020-10-21T12:39:06.000+00:00",
			//                 "endDate": "2020-10-21T13:38:06.000+00:00"
			//             },
			//             {
			//                 "startDate": "2020-01-21T13:39:06.000+00:00",
			//                 "endDate": "2020-01-23T14:38:06.000+00:00"
			//             }
			//         ],
			//         comments: [
			//             {
			//                 "id": 2,
			//                 "text": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
			//             },
			//             {
			//                 "id": 3,
			//                 "text": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
			//             }
			//         ]
			//     },

			// ]
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
	},

	methods: {
		...mapActions(["getUserRentingHistory", "postComment"]),
		openCommentDialog: function() {
			this.$v.$reset();
			this.commentContent = "";
			this.commentTitle = "";
		},
		commentOnAd: function(ad) {
			let comment = {
				title: this.commentTitle,
				content: this.commentContent,
				userId: this.getUser.id,
				advertisementId: ad.advertisement.id,
				rentingRequestId: ad.rentingRequestId,
			};

			this.$store
				.dispatch("postComment", comment)
				.then((data) => {
					this.showCommentDialog = false;
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
		},
		commentTitle: {
			required,
		},
	},
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
