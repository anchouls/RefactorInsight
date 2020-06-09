package data.types.attributes;

import com.intellij.openapi.project.Project;
import data.Group;
import data.RefactoringInfo;
import data.types.Handler;
import gr.uom.java.xmi.diff.ChangeAttributeTypeRefactoring;
import org.refactoringminer.api.Refactoring;
import utils.Utils;

public class ChangeAttributeTypeHandler extends Handler {

  @Override
  public RefactoringInfo specify(Refactoring refactoring, RefactoringInfo info, Project project) {
    ChangeAttributeTypeRefactoring ref = (ChangeAttributeTypeRefactoring) refactoring;

    String classNameBefore = ref.getClassNameBefore();
    String classNameAfter = ref.getClassNameAfter();

    return info.setGroup(Group.ATTRIBUTE)
        .setGroupId(ref.getClassNameAfter() + "." + ref.getChangedTypeAttribute().getVariableName())
        .setNameBefore(classNameBefore)
        .setNameAfter(classNameAfter)
        .setElementBefore(ref.getOriginalAttribute().getVariableDeclaration().toQualifiedString())
        .setElementAfter(ref.getChangedTypeAttribute().getVariableDeclaration().toQualifiedString())
        .addMarking(ref.getOriginalAttribute().getType().codeRange(),
            ref.getChangedTypeAttribute().getType().codeRange());

  }
}
