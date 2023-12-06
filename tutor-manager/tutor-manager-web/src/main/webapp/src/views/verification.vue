<template>
    <div class="container">
        <div class="message-container">
            <h1>Email Verification</h1>
            <p>We've sent a verification email to the address you provided. Please check your inbox (and spam folder, just in case) for an email from us.</p>
            <p>Enter the verification code from the email below:</p>
            <form @submit.prevent="submitForm">
              <div class="input-div">
                <input type="text" v-model="verificationCode" name="verification_code" placeholder="Verification Code" required>
              </div>
              <button type="submit" class="btn">Verify</button>
            </form>
            <p>If you don't receive the email within a few minutes or need assistance, please contact our support team.</p>
        </div>
    </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import common from '../api/commonAPI';

export default {
  name: 'VarificationPage',
  props: {
    email: String,
    userType: String
  },
  data() {
    return {
      verificationCode: ''
    };
  },
  methods: {
    async submitForm() {
      //Define API endpoint
      const endpoint = `api/verify/verifyCode`;
      console.log(this.email, this.userType)

      // Prepare data
      const dataToSend = {
        email: this.email,
        code: this.verificationCode
      };

      try {
        console.log(dataToSend);
        // Call API
        const response = await common(endpoint, dataToSend, 'POST');

        if(response.data && response.data.code === 1) {
          alert('Verification successful!');
          this.$router.push({
            name: 'ResetPW',
            query: { email: this.email, userType: this.userType }
          });
        } else {
          alert('Verification failed. Please check your input and try again.');
        }
      } catch(error) {
        console.error('Error during verification:', error);
        alert('An error occurred while verifying. Please try again later.');
      }
    },
    addcl(inputType) {
      if (inputType === 'email') {
        let parent = this.$refs.emailInputDiv;
        parent.classList.add('focus');
      }
    },
    remcl(inputType) {
      if (inputType === 'email') {
        let parent = this.$refs.emailInputDiv;
        if (this.$refs.emailInput.value === '') {
          parent.classList.remove('focus');
        }
      }
    },
  },
};
</script>

<style>
* {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Poppins', sans-serif;
    overflow: hidden;
    background-color: #f1f1f1; 
}

.container {
    width: 100vw;
    height: 100vh;
    display: grid;
    grid-gap: 7rem;
    padding: 0 2rem;
}

.message-container {
    margin:auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    background-color: #f1f1f1;
    padding: 2rem;
    border-radius: 10px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
}

.message-container h1 {
    color: #333;
    font-size: 2.4rem;
    margin-bottom: 1rem;
}

.message-container p {
    color: #666;
    font-size: 1rem;
    margin-bottom: 0.5rem;
}

.input-div input {
    width: 100%;
    padding: 10px;
    margin-top: 10px;
    border: 2px solid #ccc;
    border-radius: 5px;
    outline: none;
    transition: border-color 0.3s ease;
}

.input-div input:focus {
    border-color: #4941ec;
    
}


.btn{
    display: block;
    width: 100%;
    height: 50px;
    border-radius: 25px;
    outline: none;
    border: none;
    background-image: linear-gradient(to right, #6C63FF, #4941ec, #32be8f);
    background-size: 200%;
    font-size: 1.2rem;
    color: #fff;
    font-family: 'Poppins', sans-serif;
    text-transform: uppercase;
    margin: 1rem 0;
    cursor: pointer;
    transition: .5s;
}
.btn:hover{
    background-position: right;
}

@media screen and (max-width: 900px) {
    .container {
        grid-template-columns: 1fr;
    }
}
</style>