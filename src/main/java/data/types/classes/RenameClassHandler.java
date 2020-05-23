package data.types.classes;

import data.RefactoringInfo;
import data.Scope;
import data.TrueCodeRange;
import data.types.Handler;
import gr.uom.java.xmi.diff.RenameClassRefactoring;
import java.util.Arrays;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringType;

public class RenameClassHandler implements Handler {

  @Override
  public RefactoringInfo handle(Refactoring refactoring) {
    RenameClassRefactoring ref = (RenameClassRefactoring) refactoring;

    return new RefactoringInfo(Scope.CLASS)
        .setType(RefactoringType.RENAME_CLASS)
        .setName(ref.getName())
        .setText(ref.toString())
        .setLeftSide(Arrays.asList(new TrueCodeRange(ref.getOriginalClass().codeRange())))
        .setRightSide(Arrays.asList(new TrueCodeRange(ref.getRenamedClass().codeRange())))
        .setNameBefore(ref.getOriginalClassName())
        .setNameAfter(ref.getRenamedClassName());
  }
}
