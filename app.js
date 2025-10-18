const API_BASE = 'http://localhost:8080/api';

// --- Tab switching ---
document.querySelectorAll('.tab').forEach(tab => {
  tab.onclick = () => {
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    tab.classList.add('active');
    document.querySelectorAll('.view').forEach(v => v.style.display = 'none');
    document.getElementById(tab.dataset.view).style.display = 'block';
    if (tab.dataset.view === 'tasksView') loadTasks();
    else loadLogs();
  };
});

// --- TASKS ---
async function loadTasks(url = `${API_BASE}/tasks`) {
  try {
    const res = await fetch(url);
    const tasks = await res.json();
    const tbody = document.querySelector('#tasksTable tbody');
    tbody.innerHTML = '';
    tasks.forEach((t, index) => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${index + 1}</td>
        <td>${escapeHtml(t.title)}</td>
        <td>${escapeHtml(t.description)}</td>
        <td>${t.createdAt ? new Date(t.createdAt).toLocaleString() : '-'}</td>
        <td>
          <button onclick="editTask('${t.id}','${escapeHtml(t.title)}','${escapeHtml(t.description)}')">Edit</button>
          <button onclick="deleteTask('${t.id}')">Delete</button>
        </td>`;
      tbody.appendChild(tr);
    });
  } catch(e) { alert('Error loading tasks: ' + e.message); }
}

// --- SEARCH & FILTER FEATURE ---
document.getElementById('btnSearch').addEventListener('click', async () => {
  const searchValue = document.getElementById('searchInput').value.trim();
  const filterValue = document.getElementById('filterDropdown').value.trim();

  let url = `${API_BASE}/tasks`;
  if (searchValue) {
    url = `${API_BASE}/tasks/search?title=${encodeURIComponent(searchValue)}`;
  } else if (filterValue) {
    url = `${API_BASE}/tasks/filter?keyword=${encodeURIComponent(filterValue)}`;
  }
  loadTasks(url);
});

// --- MODAL ---
const modal = document.getElementById('taskModal');
const titleInput = document.getElementById('taskTitle');
const descInput = document.getElementById('taskDesc');
let editId = null;

document.getElementById('btnAddTask').onclick = () => openModal();
document.getElementById('btnCancel').onclick = () => closeModal();
document.getElementById('btnSave').onclick = saveTask;

function openModal(task=null){
  editId = task ? task.id : null;
  titleInput.value = task ? task.title : '';
  descInput.value = task ? task.description : '';
  document.getElementById('modalTitle').innerText = editId ? 'Edit Task' : 'New Task';
  modal.style.display = 'flex';
}
function closeModal(){
  modal.style.display = 'none';
  titleInput.value = descInput.value = '';
  editId = null;
}
async function saveTask(){
  const body = JSON.stringify({ title: titleInput.value, description: descInput.value });
  const method = editId ? 'PUT' : 'POST';
  const url = editId ? `${API_BASE}/tasks/${editId}` : `${API_BASE}/tasks`;
  try { await fetch(url, { method, headers: {'Content-Type':'application/json'}, body }); closeModal(); loadTasks(); }
  catch(e){ alert('Error saving task: ' + e.message); }
}
async function deleteTask(id){
  if (!confirm('Delete this task?')) return;
  try { await fetch(`${API_BASE}/tasks/${id}`, { method:'DELETE' }); loadTasks(); }
  catch(e){ alert('Error deleting task: ' + e.message); }
}
function editTask(id, title, desc){ openModal({id, title, description: desc}); }

// --- LOGS ---
let currentLogPage = 0;
const logPageSize = 5;
let totalLogPages = 1;

async function loadLogs() {
  try {
    const res = await fetch(`${API_BASE}/logs?page=${currentLogPage}&size=${logPageSize}`);
    const data = await res.json();

    const logs = data._embedded?.auditLogList || [];
    totalLogPages = data.page?.totalPages || 1;

    const tbody = document.querySelector('#logsTable tbody');
    tbody.innerHTML = '';

    logs.forEach((log, index) => {
      const changes = log.updatedContent || {};

      // ✅ Format "From/To" structure
      const formattedChanges = Object.entries(changes)
        .map(([key, value]) => {
          const label = key.replace(/_/g, ' ');
          if (value && typeof value === 'object' && 'old' in value && 'new' in value) {
            return `${label}\nFrom: ${value.old || '(empty)'}\nTo:   ${value.new || '(empty)'}`;
          } else {
            return `${label}: ${JSON.stringify(value)}`;
          }
        })
        .join('\n\n');

      const actionClass = log.action === 'CREATE'
        ? 'log-create'
        : log.action === 'UPDATE'
          ? 'log-update'
          : 'log-delete';

      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${index + 1 + currentLogPage * logPageSize}</td>
        <td>${new Date(log.timestamp).toLocaleString()}</td>
        <td class="${actionClass}">${log.action}</td>
        <td>${log.taskId || '-'}</td>
        <td><pre style="white-space:pre-wrap">${escapeHtml(formattedChanges)}</pre></td>
      `;
      tbody.appendChild(tr);
    });

    document.getElementById('logPageInfo').innerText = `Page ${currentLogPage + 1} / ${totalLogPages}`;
    document.getElementById('prevLog').disabled = currentLogPage === 0;
    document.getElementById('nextLog').disabled = currentLogPage + 1 >= totalLogPages;
  } catch (e) {
    alert('Failed loading logs: ' + e.message);
  }
}



// Prev / Next buttons
document.getElementById('prevLog').onclick = () => {
  if (currentLogPage > 0) {
    currentLogPage--;
    loadLogs();
  }
};
document.getElementById('nextLog').onclick = () => {
  if (currentLogPage + 1 < totalLogPages) {
    currentLogPage++;
    loadLogs();
  }
};

async function saveTask() {
  const title = titleInput.value.trim();
  const desc = descInput.value.trim();

  // ✅ Validation: stop if either field is empty
  if (!title || !desc) {
    alert("Please enter both title and description.");
    return; // Prevent sending data to backend
  }

  const body = JSON.stringify({ title, description: desc });
  const method = editId ? 'PUT' : 'POST';
  const url = editId ? `${API_BASE}/tasks/${editId}` : `${API_BASE}/tasks`;

  try {
    await fetch(url, { method, headers: { 'Content-Type': 'application/json' }, body });
    closeModal();
    loadTasks();
  } catch (e) {
    alert('Error saving task: ' + e.message);
  }
}


// --- HELPERS ---
function escapeHtml(s){ return String(s||'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }

// --- INITIAL LOAD ---
loadTasks();

