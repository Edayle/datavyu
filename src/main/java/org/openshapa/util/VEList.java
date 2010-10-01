package org.openshapa.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import org.openshapa.models.db.legacy.FormalArgument;
import org.openshapa.views.discrete.datavalues.vocabelements.VocabElementV;

/**
 * Vocab Element List
 * Class that emulates a vector of vocab elements.
 * Provides functionality for undo and redo by maintaining sets of arguments
 * and VocabElementV statuses.
 *
 * @author Nick Stevens
 */
public class VEList extends Vector<VocabElementV>{

    /* a set of argument sets for the vocab elements */
    Vector<Vector<FormalArgument>> argSet;
    /* a set of booleans to identify elements to be deleted */
    Vector<Boolean> deleted;
    /* a set of booleans to identify elements that have changed */
    Vector<Boolean> changes;

    /*
     * Initialise a normal Vector<VocabElementV>
     */
    public VEList(){
        super();
    }

    /*
     * Save and return the current vocab element list.
     * Maintain the vocab elements in the list, with their repective arguments,
     * the status of the deleteVE and hasVEChanged variables.
     *
     * @return a copy of this VEList
     */
    public VEList getClone(){
        try{
            deleted = new Vector<Boolean>();
            changes = new Vector<Boolean>();
            argSet = new Vector<Vector<FormalArgument>>();
            Vector<FormalArgument> args;
            Enumeration<VocabElementV> veView = super.elements();
        while(veView.hasMoreElements()){
            VocabElementV vev = veView.nextElement();
            deleted.add(vev.isDeletable());
            changes.add(vev.hasChanged());
            args = new Vector<FormalArgument>();
            int numArgs = vev.getModel().getNumFormalArgs();
            for(int i = 0; i < numArgs;i++){
                args.add(vev.getModel().getFormalArgCopy(i));
            }
            argSet.add((Vector<FormalArgument>) args.clone());
        }
        }catch(Exception e){}


        return (VEList) this.clone();
    }

    /*
     * Update all the values in the vocab element list so that each element has
     * the right arguments as well as indicators of deletion or change.
     */
    public void setClone(){
        try{
        Enumeration<VocabElementV> veView = super.elements();
        int i = 0;
        while(veView.hasMoreElements()){
            VocabElementV vev = veView.nextElement();
            for(int j = vev.getModel().getNumFormalArgs()-1; j >= 0;j--){
                vev.getModel().deleteFormalArg(j);
            }
            for(int j = 0; j< argSet.get(i).size(); j++){
                vev.getModel().appendFormalArg(argSet.get(i).get(j));
            }
            vev.setDeleted(deleted.get(i));
            vev.setHasChanged(changes.get(i));
            i++;
            vev.rebuildContents();
        }

        }catch(Exception e){}
    }



}