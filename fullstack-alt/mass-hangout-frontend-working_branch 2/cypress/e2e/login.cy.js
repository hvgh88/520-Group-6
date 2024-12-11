
describe('Login Page Tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/login'); 
  });

  it('should display the login form correctly', () => {
    cy.get('h2').contains('Welcome back!');
    cy.get('label').contains('Email');
    cy.get('label').contains('Password');
    cy.get('button[type="submit"]').contains('Sign in');
  });

  it('should show an error message for invalid email format', () => {
    cy.get('input[type="email"]').type('invalid-email').blur();

    
    cy.get('.error-message').contains('Invalid email format.');
  });

  it('should not show an email error for valid email format', () => {
    
    cy.get('input[type="email"]').type('test@umass.edu').blur();

    
    cy.get('.error-message').should('not.exist');
  });

  it('should display a general error message for network issues', () => {
    
    cy.get('input[type="email"]').type('test@umass.edu');
    cy.get('input[type="password"]').type('correctpassword');

    
    cy.intercept('POST', '**/login', {
      forceNetworkError: true,
    });

   
    cy.get('button[type="submit"]').click();

    cy.get('.error-message').contains('Network error. Please check your connection.');
  });
});
