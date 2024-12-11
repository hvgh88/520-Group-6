describe('Profile Page Tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/profile'); 
  });

  it('should display the profile page correctly', () => {
    
    cy.get('.profile-header h2').should('exist');
    cy.get('.profile-details').should('exist');
    cy.get('button.edit-button').should('exist');
  });

  it('should fetch and display user profile data correctly', () => {
    
    cy.intercept('GET', '**/profile/*', {
      statusCode: 200,
      body: {
        code: 10006,
        data: {
          firstName: 'John',
          middleName: '',
          lastName: 'Doe',
          email: 'john@umass.edu',
          department: 'Computer Science',
          graduationYear: '2025',
          bio: 'CS student interested in web development and AI',
        },
      },
    }).as('getProfile');

    
    cy.reload();

 
    cy.wait('@getProfile');


    cy.get('.profile-header h2').contains('John Doe');
    cy.get('.profile-details').within(() => {
      cy.contains('john@umass.edu');
      cy.contains('Computer Science');
      cy.contains('Class of 2025');
      cy.contains('CS student interested in web development and AI');
    });
  });

  it('should enable editing mode when the edit button is clicked', () => {
    
    cy.get('button.edit-button').click();

   
    cy.get('.edit-form').should('be.visible');
  });
});
