package backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class represents the classic recursive backtracking algorithm.
 * It has a solver that can take a valid configuration and return a
 * solution, if one exists.
 *
 * This file comes from the backtracking lab. It should be useful
 * in this project. A second method has been added that you should
 * implement.
 *
 * @author Sean Strout @ RIT CS
 * @author James Heliotis @ RIT CS
 * @author Bryan Quinn
 * @author Chris Cassidy
 * */
public class Backtracker {

    private boolean debug;
    private SafeConfig config;

    /**
     * Initialize a new backtracker.
     *
     * @param debug Is debugging output enabled?
     */
    public Backtracker(boolean debug) {
        this.debug = debug;
        if (this.debug) {
            System.out.println("Backtracker debugging enabled...");
        }
    }

    /**
     * calls the backtracker to run
     * @param filename the file it is reading
     */
    public Backtracker(String filename){
        config = new SafeConfig(filename);
        Optional solution = this.solve(config);
        if(solution.isPresent()) {
            this.config = (SafeConfig) solution.get();
        }
        else {
            this.config = null;
        }
    }

    /**
     * returns the grid.
     * @return the grid
     */
    public Configuration getSolution(){
        return config;
    }

    /**
     * A utility routine for printing out various debug messages.
     *
     * @param msg    The type of config being looked at (current, goal,
     *               successor, e.g.)
     * @param config The config to display
     */
    private void debugPrint(String msg, Configuration config) {
        if (this.debug) {
            System.out.println(msg + ":\n" + config);
        }
    }

    /**
     * Try find a solution, if one exists, for a given configuration.
     *
     * @param config A valid configuration
     * @return A solution config, or null if no solution
     */
    public Optional<Configuration> solve(Configuration config) {
        debugPrint("Current config", config);
        if(!config.getIsRip()){
            if (config.isGoal()) {
                debugPrint("\tGoal config", config);
                return Optional.of(config);
            } else {
                for (Configuration child : config.getSuccessors()) {
                    if (child.isValid()) {
                        debugPrint("\tValid successor", child);
                        Optional<Configuration> sol = solve(child);
                        if (sol.isPresent()) {
                            return sol;
                        }
                    } else {
                        debugPrint("\tInvalid successor", child);
                    }
                }
                // implicit backtracking happens here
            }
        }
        return Optional.empty();
    }

    /**
     * Find a goal configuration if it exists, and how to get there.
     *
     * @param current the starting configuration
     * @return a list of configurations to get to a goal configuration.
     * If there are none, return null.
     */
    public List<Configuration> solveWithPath(Configuration current) {
        ArrayList<Configuration> possible = new ArrayList<>();
        if(!current.getIsRip()){
            if (current.isGoal()) {
                debugPrint("\tGoal config", current);
                possible.add(current);
                return possible;
            } else {
                for (Configuration child : current.getSuccessors()) {
                    if (child.isValid()) {
                        debugPrint("\tValid successor", child);
                        Optional<Configuration> sol = solve(child);
                        if (sol.isPresent()) {
                            possible.add(sol.get());
                        }
                    } else {
                        debugPrint("\tInvalid successor", child);
                    }
                }
                possible.addAll(solveWithPath(config));
                return possible;
                // implicit backtracking happens here
            }
        }
        return null;

    }
}
