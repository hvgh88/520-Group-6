describe('Groups Page Tests', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/groups'); 
  });  

  it('should allow downloading an invite', () => {
    cy.intercept('GET', '**/api/group/1/ics', {
      statusCode: 200,
      body: 'BEGIN:VCALENDAR\nVERSION:2.0\n...',
      headers: {
        'Content-Type': 'text/calendar',
      },
    }).as('downloadInvite');

    cy.contains('Download Invite').click();

    cy.wait('@downloadInvite');

    cy.contains('Download Invite').should('exist');
  });
});
