document.addEventListener('DOMContentLoaded', function() {
    const drawnTeams = JSON.parse(sessionStorage.getItem('drawnTeams'));
    if (drawnTeams) {
        displayDrawResults(drawnTeams);
        sessionStorage.removeItem('drawnTeams'); // Nettoyage après utilisation
    }
});

function displayDrawResults(teams) {
    const resultsContainer = document.getElementById('resultsContainer');
    let html = `
    <table>
      <thead>
        <tr>
          <th>TEAMS</th>
          <th class="pot-header" colspan="2">POT 1</th>
          <th class="pot-header" colspan="2">POT 2</th>
          <th class="pot-header" colspan="2">POT 3</th>
          <th class="pot-header" colspan="2">POT 4</th>
        </tr>
        <tr>
          <th></th>
          <th>HOME</th>
          <th>AWAY</th>
          <th>HOME</th>
          <th>AWAY</th>
          <th>HOME</th>
          <th>AWAY</th>
          <th>HOME</th>
          <th>AWAY</th>
        </tr>
      </thead>
      <tbody>
    `;

    teams.forEach(team => {
        html += `
        <tr>
          <td>
            <div class="team-info">
              <img class="team-logo" alt="${team.name} logo" src="/logos/${team.logoUrl}">
              <span class="team-name">${team.name}</span>
            </div>
          </td>
        `;
        team.allOpponentsNames.forEach(opponent => {
            html += `<td>${opponent}</td>`;
        });
        html += `</tr>`;
    });

    html += `
      </tbody>
    </table>
    `;

    resultsContainer.innerHTML = html;
}