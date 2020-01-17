/**
 * 
 */
$(document).ready(function() {
 
        $('#password').keyup(function() {
            var password = $('#password').val();
            if (checkStrength(password) == false) {
                $('#sign-up').attr('disabled', true);
            }
        });
        $('#matchPassword').blur(function() {
            if ($('#password').val() !== $('#matchPassword').val()) {
                $('#globalError').removeClass('hide');
                $('#sign-up').attr('disabled', true);
            } else {
                $('#globalError').addClass('hide');
            }
        });
 
 
 
        function checkStrength(password) {
            var strength = 0;
 
 
            //If password contains both lower and uppercase characters, increase strength value.
            if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
                strength += 1;
                $('.low-upper-case').addClass('text-success');
                $('.low-upper-case i').removeClass('fa-times').addClass('fa-check');
                $('#popover-password-top').addClass('hide');
 
 
            } else {
                $('.low-upper-case').removeClass('text-success');
                $('.low-upper-case i').addClass('fa-times').removeClass('fa-check');
                $('#popover-password-top').removeClass('hide');
            }
 
            //If it has numbers and characters, increase strength value.
            if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) {
                strength += 1;
                $('.one-number').addClass('text-success');
                $('.one-number i').removeClass('fa-times').addClass('fa-check');
                $('#popover-password-top').addClass('hide');
 
            } else {
                $('.one-number').removeClass('text-success');
                $('.one-number i').addClass('fa-times').removeClass('fa-check');
                $('#popover-password-top').removeClass('hide');
            }
 
            //If it has one special character, increase strength value.
            if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) {
                strength += 1;
                $('.one-special-char').addClass('text-success');
                $('.one-special-char i').removeClass('fa-times').addClass('fa-check');
                $('#popover-password-top').addClass('hide');
 
            } else {
                $('.one-special-char').removeClass('text-success');
                $('.one-special-char i').addClass('fa-times').removeClass('fa-check');
                $('#popover-password-top').removeClass('hide');
            }
 
            if (password.length > 7) {
                strength += 1;
                $('.eight-character').addClass('text-success');
                $('.eight-character i').removeClass('fa-times').addClass('fa-check');
                $('#popover-password-top').addClass('hide');
 
            } else {
                $('.eight-character').removeClass('text-success');
                $('.eight-character i').addClass('fa-times').removeClass('fa-check');
                $('#popover-password-top').removeClass('hide');
            }
 
 
            // If value is less than 2
 
            if (strength < 2) {
                $('#result').removeClass()
                $('#password-strength').addClass('progress-bar-danger progress-bar-striped');
 
                $('#result').addClass('text-danger').text('Very Week');
                $('#password-strength').css('width', '10%');
            } else if (strength == 2) {
                $('#result').addClass('good');
                $('#password-strength').removeClass('progress-bar-danger progress-bar-striped');
                $('#password-strength').addClass('progress-bar-warning progress-bar-striped');
                $('#result').addClass('text-warning').text('Week')
                $('#password-strength').css('width', '60%');
                return 'Week'
            } else if (strength == 4) {
                $('#result').removeClass()
                $('#result').addClass('strong');
                $('#password-strength').removeClass('progress-bar-warning progress-bar-striped');
                $('#password-strength').addClass('progress-bar-success progress-bar-striped');
                $('#result').addClass('text-success').text('Strength');
                $('#password-strength').css('width', '100%');
 
                return 'Strong'
            }
 
        }
 
    });