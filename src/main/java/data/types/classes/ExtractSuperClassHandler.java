package data.types.classes;

import data.RefactoringInfo;
import data.Scope;
import data.TrueCodeRange;
import data.types.Handler;
import gr.uom.java.xmi.diff.ExtractSuperclassRefactoring;
import java.util.stream.Collectors;
import org.refactoringminer.api.Refactoring;

public class ExtractSuperClassHandler implements Handler {

  @Override
  public RefactoringInfo handle(Refactoring refactoring) {
    ExtractSuperclassRefactoring ref = (ExtractSuperclassRefactoring) refactoring;
    return new RefactoringInfo(Scope.CLASS)
        .setType(ref.getRefactoringType())
        .setName(ref.getName())
        .setText(ref.toString())
        .setLeftSide(ref.leftSide().stream().map(TrueCodeRange::new).collect(Collectors.toList()))
        .setRightSide(
            ref.rightSide().stream().map(TrueCodeRange::new).collect(Collectors.toList()));
  }
}
