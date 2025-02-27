package com.compomics.colims.client.controller;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import com.compomics.colims.client.distributed.QueueManager;
import com.compomics.colims.client.distributed.producer.DbTaskProducer;
import com.compomics.colims.client.event.*;
import com.compomics.colims.client.event.message.MessageEvent;
import com.compomics.colims.client.event.message.StorageQueuesConnectionErrorMessageEvent;
import com.compomics.colims.client.event.message.UnexpectedErrorMessageEvent;
import com.compomics.colims.client.model.table.format.ExperimentManagementTableFormat;
import com.compomics.colims.client.model.table.format.ProjectManagementTableFormat;
import com.compomics.colims.client.model.table.format.SampleManagementTableFormat;
import com.compomics.colims.client.view.ProjectManagementPanel;
import com.compomics.colims.core.distributed.model.DeleteDbTask;
import com.compomics.colims.core.service.ProjectService;
import com.compomics.colims.core.service.SampleService;
import com.compomics.colims.core.service.UserService;
import com.compomics.colims.model.*;
import com.compomics.colims.model.comparator.IdComparator;
import com.compomics.colims.model.enums.DefaultPermission;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The project management view controller.
 *
 * @author Niels Hulstaert
 */
@Component("projectManagementController")
public class ProjectManagementController implements Controllable {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManagementController.class);

    //model
    private AdvancedTableModel<Project> projectsTableModel;
    private DefaultEventSelectionModel<Project> projectsSelectionModel;
    private final EventList<Experiment> experiments = new BasicEventList<>();
    private AdvancedTableModel<Experiment> experimentsTableModel;
    private DefaultEventSelectionModel<Experiment> experimentsSelectionModel;
    private final EventList<Sample> samples = new BasicEventList<>();
    private AdvancedTableModel<Sample> samplesTableModel;
    private DefaultEventSelectionModel<Sample> samplesSelectionModel;
    @Autowired
    private UserBean userBean;
    //view
    private ProjectManagementPanel projectManagementPanel;
    //child controller
    @Autowired
    @Lazy
    private ProjectEditController projectEditController;
    @Autowired
    @Lazy
    private ExperimentEditController experimentEditController;
    @Autowired
    @Lazy
    private SampleEditController sampleEditController;
    @Autowired
    @Lazy
    private SampleRunsController analyticalRunsSearchSettingsController;
    @Autowired
    @Lazy
    private AnalyticalRunsAdditionController analyticalRunsAdditionController;
    @Autowired
    @Lazy
    private MzTabExportController mzTabExportController;
    //parent controller
    @Autowired
    private MainController mainController;
    //services
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private UserService userService;
    @Autowired
    private DbTaskProducer dbTaskProducer;
    @Autowired
    private QueueManager queueManager;
    @Autowired
    private EventBus eventBus;

    /**
     * Get the view of this controller.
     *
     * @return the ProjectManagementPanel
     */
    public ProjectManagementPanel getProjectManagementPanel() {
        return projectManagementPanel;
    }

    @Override
    public void init() {
        //register to event bus
        eventBus.register(this);

        //init view
        projectManagementPanel = new ProjectManagementPanel();

        //init projects table
        SortedList<Project> sortedProjects = new SortedList<>(mainController.getProjects(), new IdComparator());
        projectsTableModel = GlazedListsSwing.eventTableModel(sortedProjects, new ProjectManagementTableFormat());
        projectManagementPanel.getProjectsTable().setModel(projectsTableModel);
        projectsSelectionModel = new DefaultEventSelectionModel<>(sortedProjects);
        projectsSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectManagementPanel.getProjectsTable().setSelectionModel(projectsSelectionModel);

        //set column widths
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.PROJECT_ID).setPreferredWidth(40);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.PROJECT_ID).setMaxWidth(40);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.PROJECT_ID).setMinWidth(40);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.TITLE).setPreferredWidth(500);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.LABEL).setPreferredWidth(250);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.LABEL).setMaxWidth(250);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.LABEL).setMinWidth(250);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.OWNER).setPreferredWidth(150);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.OWNER).setMaxWidth(150);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.OWNER).setPreferredWidth(150);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.CREATED).setPreferredWidth(120);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.CREATED).setMaxWidth(120);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.CREATED).setMinWidth(120);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.NUMBER_OF_EXPERIMENTS).setPreferredWidth(120);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.NUMBER_OF_EXPERIMENTS).setMaxWidth(120);
        projectManagementPanel.getProjectsTable().getColumnModel().getColumn(ProjectManagementTableFormat.NUMBER_OF_EXPERIMENTS).setMinWidth(120);

        //init projects experiment table
        SortedList<Experiment> sortedExperiments = new SortedList<>(experiments, new IdComparator());
        experimentsTableModel = GlazedListsSwing.eventTableModel(sortedExperiments, new ExperimentManagementTableFormat());
        projectManagementPanel.getExperimentsTable().setModel(experimentsTableModel);
        experimentsSelectionModel = new DefaultEventSelectionModel<>(sortedExperiments);
        experimentsSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectManagementPanel.getExperimentsTable().setSelectionModel(experimentsSelectionModel);

        //set column widths
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.EXPERIMENT_ID).setPreferredWidth(40);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.EXPERIMENT_ID).setMaxWidth(40);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.EXPERIMENT_ID).setMinWidth(40);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.TITLE).setPreferredWidth(500);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.NUMBER).setPreferredWidth(150);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.NUMBER).setMaxWidth(150);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.NUMBER).setMinWidth(150);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.CREATED).setPreferredWidth(120);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.CREATED).setMaxWidth(120);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.CREATED).setMinWidth(120);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.NUMBER_OF_SAMPLES).setPreferredWidth(120);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.NUMBER_OF_SAMPLES).setMaxWidth(120);
        projectManagementPanel.getExperimentsTable().getColumnModel().getColumn(ExperimentManagementTableFormat.NUMBER_OF_SAMPLES).setMinWidth(120);

        //init experiment samples table
        SortedList<Sample> sortedSamples = new SortedList<>(samples, new IdComparator());
        samplesTableModel = GlazedListsSwing.eventTableModel(sortedSamples, new SampleManagementTableFormat());
        projectManagementPanel.getSamplesTable().setModel(samplesTableModel);
        samplesSelectionModel = new DefaultEventSelectionModel<>(sortedSamples);
        samplesSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        projectManagementPanel.getSamplesTable().setSelectionModel(samplesSelectionModel);

        //set column widths
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.SAMPLE_ID).setPreferredWidth(40);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.SAMPLE_ID).setMaxWidth(40);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.SAMPLE_ID).setMinWidth(40);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.NAME).setPreferredWidth(500);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.CONDITION).setPreferredWidth(200);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.CONDITION).setMaxWidth(200);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.CONDITION).setMinWidth(200);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.PROTOCOL).setPreferredWidth(200);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.PROTOCOL).setMaxWidth(200);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.PROTOCOL).setMinWidth(200);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.CREATED).setPreferredWidth(120);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.CREATED).setMaxWidth(120);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.CREATED).setMinWidth(120);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.NUMBER_OF_RUNS).setPreferredWidth(120);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.NUMBER_OF_RUNS).setMaxWidth(120);
        projectManagementPanel.getSamplesTable().getColumnModel().getColumn(SampleManagementTableFormat.NUMBER_OF_RUNS).setMinWidth(120);

        //set sorting
        @SuppressWarnings("UnusedAssignment")
        TableComparatorChooser projectsTableSorter = TableComparatorChooser.install(
                projectManagementPanel.getProjectsTable(), sortedProjects, TableComparatorChooser.SINGLE_COLUMN);
        TableComparatorChooser experimentsTableSorter = TableComparatorChooser.install(
                projectManagementPanel.getExperimentsTable(), sortedExperiments, TableComparatorChooser.SINGLE_COLUMN);
        TableComparatorChooser samplesTableSorter = TableComparatorChooser.install(
                projectManagementPanel.getSamplesTable(), sortedSamples, TableComparatorChooser.SINGLE_COLUMN);

        //add action listeners
        //add action listeners to other sample actions popup menu items
        SampleMenuActionListener samplePopupMenuActionListener = new SampleMenuActionListener();
        projectManagementPanel.getAddRunsMenuItem().addActionListener(samplePopupMenuActionListener);
        projectManagementPanel.getMzTabExportMenuItem().addActionListener(samplePopupMenuActionListener);
        projectManagementPanel.getViewRunsMenuItem().addActionListener(samplePopupMenuActionListener);

        projectsSelectionModel.addListSelectionListener(lse -> {
            if (!lse.getValueIsAdjusting()) {
                Project selectedProject = getSelectedProject();
                if (selectedProject != null) {
                    //fill project experiments table
                    GlazedLists.replaceAll(experiments, selectedProject.getExperiments(), false);
                } else {
                    GlazedLists.replaceAll(experiments, new ArrayList<>(), false);
                }
            }
        });

        projectManagementPanel.getAddProjectButton().addActionListener(e -> projectEditController.updateView(createDefaultProject()));

        projectManagementPanel.getEditProjectButton().addActionListener(e -> {
            Project selectedProject = getSelectedProject();
            if (selectedProject != null) {
                projectEditController.updateView(selectedProject);
            } else {
                eventBus.post(new MessageEvent("Project selection", "Please select a project to edit.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getDeleteProjectButton().addActionListener(e -> {
            Project projectToDelete = getSelectedProject();
            if (projectToDelete != null) {
                boolean deleteConfirmation = deleteEntity(projectToDelete, Project.class);
                if (deleteConfirmation) {
                    //clear the selection
                    projectsSelectionModel.clearSelection();
                }
            } else {
                eventBus.post(new MessageEvent("Project selection", "Please select a project to delete.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        experimentsSelectionModel.addListSelectionListener(lse -> {
            if (!lse.getValueIsAdjusting()) {
                Experiment selectedExperiment = getSelectedExperiment();
                if (selectedExperiment != null) {
                    //fill samples table
                    GlazedLists.replaceAll(samples, selectedExperiment.getSamples(), false);
                } else {
                    GlazedLists.replaceAll(samples, new ArrayList<>(), false);
                }
            }
        });

        projectManagementPanel.getAddExperimentButton().addActionListener(e -> {
            if (getSelectedProject() != null) {
                experimentEditController.updateView(createDefaultExperiment());
            } else {
                eventBus.post(new MessageEvent("Experiment addition", "Please select a project to add an experiment to.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getEditExperimentButton().addActionListener(e -> {
            Experiment selectedExperiment = getSelectedExperiment();
            if (selectedExperiment != null) {
                experimentEditController.updateView(selectedExperiment);
            } else {
                eventBus.post(new MessageEvent("Experiment selection", "Please select an experiment to edit.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getDeleteExperimentButton().addActionListener(e -> {
            Experiment experimentToDelete = getSelectedExperiment();
            if (experimentToDelete != null) {
                //send DeleteDbTask to DbTask queue
                boolean deleteConfirmation = deleteEntity(experimentToDelete, Experiment.class);
                if (deleteConfirmation) {
                    //clear the selection
                    experimentsSelectionModel.clearSelection();
                }
            } else {
                eventBus.post(new MessageEvent("Experiment selection", "Please select an experiment to delete.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getAddSampleButton().addActionListener(e -> {
            if (getSelectedExperiment() != null) {
                sampleEditController.updateView(createDefaultSample());
            } else {
                eventBus.post(new MessageEvent("Sample addition", "Please select an experiment to add a sample to.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getEditSampleButton().addActionListener(e -> {
            Sample selectedSample = getSelectedSample();
            if (selectedSample != null) {
                sampleEditController.updateView(selectedSample);
            } else {
                eventBus.post(new MessageEvent("Sample selection", "Please select a sample to edit.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getDeleteSampleButton().addActionListener(e -> {
            Sample sampleToDelete = getSelectedSample();
            if (sampleToDelete != null) {
                boolean deleteConfirmation = deleteEntity(sampleToDelete, Sample.class);
                if (deleteConfirmation) {
                    //clear the selection
                    samplesSelectionModel.clearSelection();
                }
            } else {
                eventBus.post(new MessageEvent("Sample selection", "Please select a sample to delete.", JOptionPane.INFORMATION_MESSAGE));
            }
        });

        projectManagementPanel.getOtherSampleActionsButton().addActionListener(e -> {
            JButton button = projectManagementPanel.getOtherSampleActionsButton();
            projectManagementPanel.getSamplePopupMenu().show(button, button.getBounds().x, button.getBounds().y + button.getBounds().height);
        });

    }

    @Override
    public void showView() {
        //do nothing
    }

    /**
     * Get the row index of the selected project in the projects table.
     *
     * @return the row index
     */
    public int getSelectedProjectIndex() {
        return projectsSelectionModel.getLeadSelectionIndex();
    }

    /**
     * Set the selected project in the projects table.
     *
     * @param index the row index
     */
    public void setSelectedProject(final int index) {
        projectsSelectionModel.clearSelection();
        projectsSelectionModel.setLeadSelectionIndex(index);
    }

    /**
     * Add a project to the projects table.
     *
     * @param project the Project instance
     */
    public void addProject(final Project project) {
        mainController.getProjects().add(project);
        eventBus.post(new ProjectChangeEvent(EntityChangeEvent.Type.CREATED, project.getId()));
    }

    /**
     * Get the number of projects in the projects table.
     *
     * @return the number of projects
     */
    public int getProjectsSize() {
        return mainController.getProjects().size();
    }

    /**
     * Get the selected project from the project overview table.
     *
     * @return the selected project, null if no project is selected
     */
    public Project getSelectedProject() {
        Project selectedProject = null;

        EventList<Project> selectedProjects = projectsSelectionModel.getSelected();
        if (!selectedProjects.isEmpty()) {
            selectedProject = selectedProjects.get(0);
        }

        return selectedProject;
    }

    /**
     * Listen to a ProjectChangeEvent and update the projects table if
     * necessary.
     *
     * @param projectChangeEvent the ProjectChangeEvent instance
     */
    @Subscribe
    public void onProjectChangeEvent(ProjectChangeEvent projectChangeEvent) {
        if (projectChangeEvent.getType().equals(EntityChangeEvent.Type.UPDATED)) {
            if (getSelectedProject() != null && getSelectedProject().getId().equals(projectChangeEvent.getProjectId())) {
                //reset the project selection
                int selectedProjectIndex = getSelectedProjectIndex();
                setSelectedProject(selectedProjectIndex);
            }
            //update the projects table UI
            projectManagementPanel.getProjectsTable().updateUI();
        }
    }

    /**
     * Get the row index of the selected experiment in the experiments table.
     *
     * @return the selected experiment index
     */
    public int getSelectedExperimentIndex() {
        return experimentsSelectionModel.getLeadSelectionIndex();
    }

    /**
     * Set the selected experiment in the experiments table.
     *
     * @param index the selected experiment index
     */
    public void setSelectedExperiment(int index) {
        experimentsSelectionModel.clearSelection();
        experimentsSelectionModel.setLeadSelectionIndex(index);
    }

    /**
     * Add an experiment to the experiments table.
     *
     * @param experiment the Experiment
     */
    public void addExperiment(final Experiment experiment) {
        experiments.add(experiment);

        //add the experiment to the selected project and update the projects table
        getSelectedProject().getExperiments().add(experiment);
        projectManagementPanel.getProjectsTable().updateUI();
    }

    /**
     * Get the number of experiments in the experiments table.
     *
     * @return the number of experiments
     */
    public int getExperimentsSize() {
        return experiments.size();
    }

    /**
     * Get the selected experiment from the experiment overview table.
     *
     * @return the selected experiment, null if no experiment is selected
     */
    public Experiment getSelectedExperiment() {
        Experiment selectedExperiment = null;

        EventList<Experiment> selectedExperiments = experimentsSelectionModel.getSelected();
        if (!selectedExperiments.isEmpty()) {
            selectedExperiment = selectedExperiments.get(0);
        }

        return selectedExperiment;
    }

    /**
     * Listen to a ExperimentChangeEvent and update the experiments table if
     * necessary.
     *
     * @param experimentChangeEvent the ExperimentChangeEvent instance
     */
    @Subscribe
    public void onExperimentChangeEvent(ExperimentChangeEvent experimentChangeEvent) {
        if (experimentChangeEvent.getType().equals(EntityChangeEvent.Type.DELETED)) {
            //if (getSelectedExperiment() != null && getSelectedExperiment().getId().equals(experimentChangeEvent.getExperimentId())) {
                //reset parent project selection
                int selectedProjectIndex = getSelectedProjectIndex();
                setSelectedProject(selectedProjectIndex);
            //}
            //update the projects table UI
            projectManagementPanel.getProjectsTable().updateUI();
        } else if (experimentChangeEvent.getType().equals(EntityChangeEvent.Type.UPDATED)) {
            if (getSelectedExperiment() != null && getSelectedExperiment().getId().equals(experimentChangeEvent.getExperimentId())) {
                //reset the experiment selection
                int selectedExperimentIndex = getSelectedExperimentIndex();
                setSelectedExperiment(selectedExperimentIndex);
            }
        }
    }

    /**
     * Get the row index of the selected sample in the samples table.
     *
     * @return the selected sample index
     */
    public int getSelectedSampleIndex() {
        return samplesSelectionModel.getLeadSelectionIndex();
    }

    /**
     * Set the selected sample in the samples table.
     *
     * @param index the selected sample index
     */
    public void setSelectedSample(final int index) {
        samplesSelectionModel.clearSelection();
        samplesSelectionModel.setLeadSelectionIndex(index);
    }

    /**
     * Add a sample to the samples table.
     *
     * @param sample the Sample
     */
    public void addSample(final Sample sample) {
        samples.add(sample);

        //add the sample to the selected experiment and update the experiments table
        getSelectedExperiment().getSamples().add(sample);
        projectManagementPanel.getExperimentsTable().updateUI();
    }

    /**
     * Get the number of samples in the samples table.
     *
     * @return the number of samples
     */
    public int getSamplesSize() {
        return samples.size();
    }

    /**
     * Get the selected sample from the sample overview table.
     *
     * @return the selected sample, null if no sample is selected
     */
    public Sample getSelectedSample() {
        Sample selectedSample = null;

        EventList<Sample> selectedSamples = samplesSelectionModel.getSelected();
        if (!selectedSamples.isEmpty()) {
            selectedSample = selectedSamples.get(0);
        }

        return selectedSample;
    }

    /**
     * Listen to a SampleChangeEvent and update the experiment and samples tables if necessary.
     *
     * @param sampleChangeEvent the sampleChangeEvent
     */
    @Subscribe
    public void onSampleChangeEvent(SampleChangeEvent sampleChangeEvent) {
        if (sampleChangeEvent.getType().equals(EntityChangeEvent.Type.DELETED)) {
            //only update the view if the project of the deleted sample is selected
            if(getSelectedProject().getId().equals(sampleChangeEvent.getProjectId())) {
                if (getSelectedExperiment() != null) {
                    //reset experiment selection
                    int selectedExperimentIndex = getSelectedExperimentIndex();
                    setSelectedExperiment(selectedExperimentIndex);
                    projectManagementPanel.getExperimentsTable().updateUI();
                }
            }
        } else if (sampleChangeEvent.getType().equals(EntityChangeEvent.Type.RUNS_ADDED)) {
            Optional<Sample> foundSample = samples.stream().filter(sample -> sample.getId().equals(sampleChangeEvent.getSampleId())).findFirst();
            foundSample.ifPresent(sample -> {
                //update the runs
                sample.setAnalyticalRuns(sampleChangeEvent.getAnalyticalRuns());
                samplesSelectionModel.clearSelection();

                //check if the sample's experiment is selected
                if (getSelectedExperiment().getId().equals(sample.getExperiment().getId())) {
                    int selectedExperimentIndex = getSelectedExperimentIndex();
                    //refresh the selection
                    experimentsSelectionModel.clearSelection();
                    experimentsSelectionModel.setLeadSelectionIndex(selectedExperimentIndex);
                }
            });
        }
    }

    /**
     * Listen to a AnalyticalRunChangeEvent and update the samples table if
     * necessary.
     *
     * @param analyticalRunChangeEvent the AnalyticalRunChangeEvent instance
     */
    @Subscribe
    public void onAnalyticalChangeEvent(AnalyticalRunChangeEvent analyticalRunChangeEvent) {
        if (analyticalRunChangeEvent.getType().equals(EntityChangeEvent.Type.DELETED)) {
            Sample selectedSample = getSelectedSample();
            if (selectedSample != null && selectedSample.getId().equals(analyticalRunChangeEvent.getParentSampleId())) {
                //reset parent sample selection
                int selectedSampleIndex = getSelectedSampleIndex();
                setSelectedSample(selectedSampleIndex);
            }
        }
    }

    /**
     * Delete the database entity (project, experiment, samples) from the
     * database. Shows a confirmation dialog first. When confirmed, a
     * DeleteDbTask JSON message is sent to the DB task queue. A message dialog
     * is shown in case the queue cannot be reached or in case of an IOException
     * thrown by the sendDbTask method.
     *
     * @param entity        the database entity to delete
     * @param dbEntityClass the database entity class
     * @return true if the delete task is confirmed.
     */
    private boolean deleteEntity(final DatabaseEntity entity, final Class dbEntityClass) {
        boolean deleteConfirmation = false;

        //check delete permissions
        if (userBean.getDefaultPermissions().get(DefaultPermission.DELETE)) {
            int option = JOptionPane.showConfirmDialog(mainController.getMainFrame(), "Are you sure? This will remove all underlying database relations (spectra, psm's, ...) as well."
                    + System.lineSeparator() + "A delete task will be sent to the database task queue.", "Delete " + dbEntityClass.getSimpleName() + " confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                //check connection
                if (queueManager.isReachable()) {
                    DeleteDbTask deleteDbTask = new DeleteDbTask(dbEntityClass, entity.getId(), userBean.getCurrentUser().getId());
                    try {
                        dbTaskProducer.sendDbTask(deleteDbTask);
                        deleteConfirmation = true;
                        eventBus.post(new MessageEvent("Delete " + dbEntityClass.getSimpleName() + " confirmation", "The delete task has been sent to the distributed module.", JOptionPane.INFORMATION_MESSAGE));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                        eventBus.post(new UnexpectedErrorMessageEvent(e.getMessage()));
                    }
                } else {
                    eventBus.post(new StorageQueuesConnectionErrorMessageEvent(queueManager.getBrokerName(), queueManager.getBrokerUrl(), queueManager.getBrokerJmxUrl()));
                }
            }
        } else {
            mainController.showPermissionErrorDialog("Your user doesn't have rights to delete this " + entity.getClass().getSimpleName());
        }

        return deleteConfirmation;
    }

    /**
     * Create a default project, with some default properties.
     *
     * @return the default project
     */
    private Project createDefaultProject() {
        Project defaultProject = new Project();

        defaultProject.setTitle("default project title");
        defaultProject.setLabel("def_label");

        //set default owner, i.e. the user with the most projects
        User userWithMostProjectOwns = projectService.getUserWithMostProjectOwns();
        if (userWithMostProjectOwns != null) {
            defaultProject.setOwner(userWithMostProjectOwns);
        } else {
            defaultProject.setOwner(userService.findAll().get(0));
        }

        return defaultProject;
    }

    /**
     * Create a default experiment, with some default properties.
     *
     * @return the default experiment
     */
    private Experiment createDefaultExperiment() {
        Experiment defaultExperiment = new Experiment();

        defaultExperiment.setTitle("default experiment title");
        defaultExperiment.setNumber(1L);

        return defaultExperiment;
    }

    /**
     * Create a default sample, with some default properties.
     *
     * @return the default sample
     */
    private Sample createDefaultSample() {
        Sample defaultSample = new Sample();

        defaultSample.setName("default sample name");
        Protocol mostUsedProtocol = sampleService.getMostUsedProtocol();
        if (mostUsedProtocol != null) {
            defaultSample.setProtocol(mostUsedProtocol);
        }

        return defaultSample;
    }

    /**
     * Inner class for listening to other sample actions menu items.
     */
    private class SampleMenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String menuItemLabel = e.getActionCommand();

            EventList<Sample> selectedSamples = samplesSelectionModel.getSelected();
            if (menuItemLabel.equals(projectManagementPanel.getAddRunsMenuItem().getText())) {
                if (selectedSamples.size() == 1) {
                    analyticalRunsAdditionController.showView();
                } else {
                    eventBus.post(new MessageEvent("Analytical run addition", "Please select one and only one sample to add the run to.", JOptionPane.INFORMATION_MESSAGE));
                }
            } else if (menuItemLabel.equals(projectManagementPanel.getMzTabExportMenuItem().getText())) {
                List<String> validationMessages = validateExportSampleSelection();
                if (validationMessages.isEmpty()) {
                    mzTabExportController.getMzTabExport().setSamples(selectedSamples);
                    mzTabExportController.showView();
                } else {
                    eventBus.post(new MessageEvent("MzTab export", validationMessages, JOptionPane.INFORMATION_MESSAGE));
                }
            } else if (menuItemLabel.equals(projectManagementPanel.getViewRunsMenuItem().getText())) {
                Sample selectedSample = getSelectedSample();
                if (selectedSamples.size() == 1 && !selectedSample.getAnalyticalRuns().isEmpty()) {
                    analyticalRunsSearchSettingsController.updateView(selectedSample);
                } else {
                    eventBus.post(new MessageEvent("Sample selection", "Please select one and only one sample with at least one run.", JOptionPane.INFORMATION_MESSAGE));
                }
            }
        }

        /**
         * Validate the samples selected for export purposes.
         *
         * @return the list of validation messages
         */
        private List<String> validateExportSampleSelection() {
            List<String> validationMessages = new ArrayList<>();

            EventList<Sample> selectedSamples = samplesSelectionModel.getSelected();
            if (selectedSamples.isEmpty()) {
                validationMessages.add("Please select one or more samples to export.");
            }
            for (Sample selectedSample : selectedSamples) {
                if (selectedSample.getAnalyticalRuns().isEmpty()) {
                    validationMessages.add("Every selected sample must have at least one run.");
                    break;
                }
            }

            return validationMessages;
        }

    }
}
