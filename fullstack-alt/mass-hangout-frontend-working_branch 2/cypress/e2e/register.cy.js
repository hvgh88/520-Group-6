describe('Register Page Tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/register'); 
  });

  it('should display the registration form correctly', () => {
    cy.get('h2').contains('Create Your Account');
    cy.get('label').contains('Email');
    cy.get('label').contains('Password');
    cy.get('button[type="submit"]').contains('Register');
  });

  it('should show an error message for invalid email format', () => {
    
    cy.get('input[type="email"]').type('invalid-email').blur();

    
    cy.get('.error-message').contains('Invalid email format.');
  });

  it('should show an error message for non-UMass email', () => {
    
    cy.get('input[type="email"]').type('test@gmail.com').blur();

    
    cy.get('.error-message').contains('Please enter valid UMass email id');
  });

  it('should not show an email error for valid UMass email', () => {
    
    cy.get('input[type="email"]').type('test@umass.edu').blur();

    
    cy.get('.error-message').should('not.exist');
  });

  it('should show an error message for invalid password', () => {
    
    cy.get('input[type="password"]').type('short').blur();

    
    cy.get('.error-message').contains('Password must be at least 8 characters long, contain letters, numbers, and at least one allowed special character');
  });

  it('should not show a password error for valid password', () => {
    
    cy.get('input[type="password"]').type('StrongPass123!').blur();

    
    cy.get('.error-message').should('not.exist');
  });

  it('should display success message and disable inputs on successful registration', () => {
    
    cy.get('input[type="email"]').type('test@umass.edu');
    cy.get('input[type="password"]').type('StrongPass123!');

    
    cy.intercept('POST', '**/register', {
      statusCode: 201,
      body: {
        code: 10001,
        message: 'Registration Successful. Please login',
      },
    });

   
    cy.get('button[type="submit"]').click();

 
    cy.get('.success-message').contains('Registration Successful. Please login');

   
    cy.get('input[type="email"]').should('be.disabled');
    cy.get('input[type="password"]').should('be.disabled');
  });

  it('should display an error message for existing user', () => {
  
    cy.get('input[type="email"]').type('existing@umass.edu');
    cy.get('input[type="password"]').type('StrongPass123!');

   
    cy.intercept('POST', '**/register', {
      statusCode: 400,
      body: {
        code: 10002,
        message: 'User already exists.',
      },
    });

    
    cy.get('button[type="submit"]').click();

    
    cy.get('.error-message').contains('User already exists.');
  });

  it('should display a general error message for network issues', () => {
    
    cy.get('input[type="email"]').type('test@umass.edu');
    cy.get('input[type="password"]').type('StrongPass123!');

    
    cy.intercept('POST', '**/register', {
      forceNetworkError: true,
    });

  
    cy.get('button[type="submit"]').click();

    
    cy.get('.error-message').contains('Network error. Please check your connection.');
  });
});
