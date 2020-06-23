<template>
    <div v-if="!isLogged">
        <form novalidate class="md-layout md-alignment-top-center" autocomplete="off" @submit.prevent="validateData">
            <md-card class="md-layout-item md-size-30 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Reset your password</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('email')">
                            <label for="first-name">E-mail</label>
                            <md-input type="email" name="email" id="email" v-model="form.email" :disabled="sending" />
                            <span class="md-error" v-if="!$v.form.email.required">Email is required</span>
                            <span class="md-error" v-else-if="!$v.form.email.email">Invalid email format</span>
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('newPassword')">
                            <label for="newPassword">New password</label>
                            <md-input id="newPassword" name="newPassword" type="password" v-model="form.newPassword" />
                            <span class="md-error" v-if="!$v.form.newPassword.required">Password is required</span>
                            <span class="md-error" v-else-if="!$v.form.newPassword.alpha">Invalid password format</span>
                            <span class="md-error" v-else-if="!$v.form.newPassword.minLength">Password requires at least 10 characters</span>
                        </md-field>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('newPasswordRetype')">
                            <label for="newPasswordRetype">Repeat new password</label>
                            <md-input id="newPasswordRetype" name="newPasswordRetype" type="password" v-model="form.newPasswordRetype" />
                            <span class="md-error" v-if="!$v.form.newPasswordRetype.required">Password is required</span>
                            <span class="md-error" v-else-if="!$v.form.newPasswordRetype.alpha">Invalid password format</span>
                            <span class="md-error" v-else-if="!$v.form.newPasswordRetype.minLength">Password requires at least 10 characters</span>
                        </md-field>
                    </div>
                </md-card-content>
                <md-card-actions>
                    <md-button type="submit" class="md-primary" >Confirm</md-button>
                </md-card-actions>
            </md-card>
        </form>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import { validationMixin } from "vuelidate";
import { required, minLength, email, helpers } from "vuelidate/lib/validators";

const sqli = helpers.regex("alpha", /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE|Select|From|Where|Script)(?=.*[A-Z])(?=.*[0-9])(?=.*\W+)([a-zA-Z0-9!?#\s?]+)$/);

export default {
    name: "ResetPassword",
    mixins: [validationMixin],
    data() {
        return {
            form: {
                email: undefined,
                newPassword: undefined,
                newPasswordRetype: undefined,
            },
            token: undefined,
            sending: false
        }
    },
    mounted() {
        this.token = this.$route.params.token;
    },
    computed: {
        ...mapGetters(["isLogged"]),
    },
    methods: {
        getValidationClass(fieldName) {
			const field = this.$v.form[fieldName];

			if (field) {
				return {
					"md-invalid": field.$invalid && field.$dirty,
				};
			}
        },
        validateData() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
                this.sending = true;
                var tokenDTO = {
                    "email": this.form.email,
                    "newPassword": this.form.newPassword,
                    "newPasswordRetype": this.form.newPasswordRetype,
                    "token": this.token
                };

				this.$store
				.dispatch("resetPassword", tokenDTO)
				.then((data) => {
                    alert(data);
                    this.$router.push("/");
				})
                .catch((error) => {
                    alert(error.response.data)
                    this.sending = false;
                });
			}
		},
    },
    validations: {
        form: {
            email: {
                required,
                email,
            },
            newPassword: {
				required,
				sqli,
				minLength: minLength(10),
			},
			newPasswordRetype: {
				required,
				sqli,
				minLength: minLength(10),
			},
        }
    }
}
</script>

<style>

</style>
