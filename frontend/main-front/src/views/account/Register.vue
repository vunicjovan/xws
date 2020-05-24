<template>
    <div>
        <transition name="fade">
            <form v-if="show" novalidate class="md-layout md-alignment-top-center" @submit.prevent="validateUser">
                <md-card class="md-layout-item md-size-30 md-small-size-100">
                    <md-card-header>
                        <div class="md-title">Register</div>
                    </md-card-header>
                    <md-card-content>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('firstName')">
                                <label for="first-name">First name</label>
                                <md-input type="text" name="first-name" id="first-name" v-model="form.firstName" autocomplete="given-name" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.firstName.required">The first name is required</span>
                                <span class="md-error" v-else-if="!$v.form.firstName.minlength">First name must have at least 3 characters</span>
                                <span class="md-error" v-else-if="!$v.form.firstName.sqli">Invalid name format</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('lastName')">
                                <label for="last-name">Last name</label>
                                <md-input type="text" name="last-name" id="last-name" v-model="form.lastName" autocomplete="family-name" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.lastName.required">The last name is required</span>
                                <span class="md-error" v-else-if="!$v.form.lastName.minlength">Last name must have at least 3 characters</span>
                                <span class="md-error" v-else-if="!$v.form.lastName.sqli">Invalid name format</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('email')">
                                <label for="first-name">E-mail</label>
                                <md-input type="email" name="email" id="email" v-model="form.email" autocomplete="email" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.email.required">Email is required</span>
                                <span class="md-error" v-else-if="!$v.form.email.email">Invalid email format</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('password')">
                                <label for="first-name">Password</label>
                                <md-input type="password" name="password" id="password" v-model="form.password" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.password.required">Password is required</span>
                                <span class="md-error" v-else-if="!$v.form.password.alpha">Invalid password format</span>
                                <span class="md-error" v-else-if="!$v.form.password.minLength">Password requires at least 8 characters</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('passwordRepeat')">
                                <label for="first-name">Repeat Password</label>
                                <md-input type="password" name="password" id="password-repeat" v-model="form.passwordRepeat" :disabled="sending" />
                                <span class="md-error" v-if="!$v.form.passwordRepeat.required">Password is required</span>
                                <span class="md-error" v-else-if="!$v.form.passwordRepeat.alpha">Invalid password format</span>
                                <span class="md-error" v-else-if="!$v.form.passwordRepeat.minLength">Password requires at least 8 characters</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-checkbox v-model="form.isAgent">Register as agent</md-checkbox>
                        </div>
                        <md-field v-if="form.isAgent">
                            <label for="company">Company</label>
                            <md-select name="company" id="company" v-model="form.companyBusinessNumber" md-dense>
                                <md-option v-for="company in companies" :key="company.businessNumber">{{ company.businessNumber }}</md-option>
                            </md-select>
                        </md-field>
                    </md-card-content>
                    <md-card-actions>
                        <md-button type="submit" class="md-primary" :disabled="sending" >Register</md-button>
                    </md-card-actions>
                </md-card>

                <md-snackbar :md-active.sync="userSaved">Registration request for user {{ lastUser }} was sent with success!</md-snackbar>
            </form>
        </transition>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
import {
    required,
    minLength,
    email
} from 'vuelidate/lib/validators'
import { helpers } from 'vuelidate/lib/validators'
const sqli = helpers.regex('alpha', /^(?!script|select|from|where|SCRIPT|SELECT|FROM|WHERE)([a-zA-Z0-9\\!\\?\\#\s?]+)$/)

export default {
    name: "Register",
    mixins: [validationMixin],
    data: function() {
        return {
            companies: null,
            form : {
                firstName: undefined,
                lastName: undefined,
                email: undefined,
                password: undefined,
                passwordRepeat: undefined,
                isAgent: false,
                companyBusinessNumber: undefined
            },
            userSaved: false,
            sending: false,
            lastUser: null,
            show: false
        }
    },
    mounted: function () {
        this.fadeMe();

        this.axios.post("http://localhost:8089/account/company/")
                      .then((response) => {
                          this.companies = response.data;
                      })
                      .catch(() => {
                          alert('Couldn\'t get registered companies!');
                      })
    },
    methods: {
        fadeMe: function() {
            this.show = !this.show
        },
        register: function() {
            this.sending = true;

            window.setTimeout(() => {
                this.lastUser = `${this.form.firstName} ${this.form.lastName}`
                this.userSaved = true
                this.sending = false
                this.clearForm()
            }, 1500)
            /*this.axios.post("http://localhost:8089/account/register", this.form)
                      .then(() => {
                          this.$router.push('/login');
                      })
                      .catch(() => {
                          alert('Registration has failed!');
                      })*/
        },
        getValidationClass (fieldName) {
            const field = this.$v.form[fieldName]

            if (field) {
                return {
                    'md-invalid': field.$invalid && field.$dirty
                }
            }
        },
        validateUser () {
            this.$v.$touch()

            if (!this.$v.$invalid) {
                this.register();
            }
        },
        clearForm () {
            this.$v.$reset()
            this.form.firstName = undefined;
            this.form.lastName = undefined;
            this.form.email = undefined;
            this.form.password = undefined;
            this.form.passwordRepeat = undefined;
            this.form.isAgent = false;
        },
    },
    validations: {
      form: {
        firstName: {
          required,
          minLength: minLength(3),
          sqli
        },
        lastName: {
          required,
          sqli,
          minLength: minLength(3)
        },
        email: {
          required,
          email
        },
        password: {
            required,
            sqli,
            minLength: minLength(8)
        },
        passwordRepeat: {
            required,
            sqli,
            minLength: minLength(8)
        }
      }
    }
    
}
</script>

<style scoped>
    .fade-enter-active,
    .fade-leave-active {
        transition: opacity 2s
    }

    .fade-enter,
    .fade-leave-to {
        opacity: 0
    }
</style>
