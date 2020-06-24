<template>
    <div v-if="!isLogged">
		<form novalidate class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateForm">
			<md-card class="md-layout-item md-size-30 md-small-size-100">
				<md-card-header>
					<div class="md-title">Password reset</div>
				</md-card-header>
				<md-card-content>
					<div class="md-layout md-gutter">
						<div class="md-layout-item md-small-size-100">
							<md-field :class="getValidationClass('email')">
								<label for="first-name">E-mail</label>
								<md-input type="email" name="email" id="email" v-model="email" :disabled="sending" />
								<span class="md-error" v-if="!$v.email.required">Email is required</span>
								<span class="md-error" v-else-if="!$v.email.email">Invalid email format</span>
							</md-field>
						</div>
					</div>
				</md-card-content>
				<md-card-actions>
					<md-button type="submit" class="md-primary">Send</md-button>
				</md-card-actions>
			</md-card>
		</form>
	</div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, email } from "vuelidate/lib/validators";
import { mapActions, mapGetters } from "vuex";

export default {
    name: "ResetEmail",
    mixins: [validationMixin],
    computed: mapGetters(["isLogged"]),
    data: function() {
		return {
            email: undefined,
            sending: false
		};
    },
    methods: {
        ...mapActions(["createResetToken"]),
        
		createToken: function() {
			this.sending = true;

            var emailVar = {
                "email": this.email
            };
            
            this.$store
				.dispatch("createResetToken", emailVar)
				.then((data) => {
					alert(data);
				})
                .catch((error) => {
                    alert(error.response.data)
                    this.sending = false;
                });
        },
        
		getValidationClass(fieldName) {
			const field = this.$v[fieldName];

			if (field) {
				return {
					"md-invalid": field.$invalid && field.$dirty,
				};
			}
        },
        
		validateForm() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.createToken();
			}
		},
	},
	validations: {
		email: {
            required,
            email,
        },
	},
}
</script>

<style>

</style>
