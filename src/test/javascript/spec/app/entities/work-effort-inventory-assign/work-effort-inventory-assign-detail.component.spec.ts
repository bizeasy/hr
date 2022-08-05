import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortInventoryAssignDetailComponent } from 'app/entities/work-effort-inventory-assign/work-effort-inventory-assign-detail.component';
import { WorkEffortInventoryAssign } from 'app/shared/model/work-effort-inventory-assign.model';

describe('Component Tests', () => {
  describe('WorkEffortInventoryAssign Management Detail Component', () => {
    let comp: WorkEffortInventoryAssignDetailComponent;
    let fixture: ComponentFixture<WorkEffortInventoryAssignDetailComponent>;
    const route = ({ data: of({ workEffortInventoryAssign: new WorkEffortInventoryAssign(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortInventoryAssignDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortInventoryAssignDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortInventoryAssignDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortInventoryAssign on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortInventoryAssign).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
